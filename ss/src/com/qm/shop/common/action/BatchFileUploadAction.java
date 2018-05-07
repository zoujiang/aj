package com.qm.shop.common.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.frame.core.action.FtpImgDownUploadAction;
import com.frame.core.constant.FtpConstant;
import com.frame.core.util.DateUtil;
import com.frame.core.util.FileUtil;
import com.frame.core.util.RandomGUID;
import com.frame.core.util.SystemConfig;
import com.frame.system.vo.UserExtForm;
import com.qm.entities.KindergartenAlbum;
import com.qm.entities.KindergartenGrade;
import com.qm.entities.KindergartenHonor;
import com.qm.entities.KindergartenPhoto;
import com.qm.entities.KindergartenStudent;
import com.qm.mapper.KindergartenAlbumMapper;
import com.qm.mapper.KindergartenGradeMapper;
import com.qm.mapper.KindergartenHonorMapper;
import com.qm.mapper.KindergartenPhotoMapper;
import com.qm.mapper.KindergartenStudentMapper;
import com.qm.shop.Constant;
import com.util.FileZipUtil;
import com.util.GradeNameUtil;

import net.sf.json.JSONObject;
/**
 * 幼儿园照片视频上传
 * */
@Controller
@RequestMapping("/fileUploadCommon")
@Scope("prototype")
public class BatchFileUploadAction extends FtpImgDownUploadAction{
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private KindergartenPhotoMapper kindergartenPhotoMapper;
	@Autowired
	private KindergartenHonorMapper kindergartenHonorMapper;
	@Autowired
	private KindergartenAlbumMapper kindergartenAlbumMapper;
	@Autowired
	private KindergartenStudentMapper kindergartenStudentMapper;
	@Autowired
	private KindergartenGradeMapper kindergartenGradeMapper;
	
	/**
	 * type  1:集体  2:geren 
	 * cate  1：照片/视频  2：荣誉
	 * */
	@RequestMapping("/files/fileUpload")
	@ResponseBody
	public String fileUpload(@RequestParam(value = "file") MultipartFile file, Integer type, String ownerId, Integer cate, HttpServletRequest request) {
		
		UserExtForm userExtForm = (UserExtForm) request.getSession().getAttribute(com.frame.core.constant.Constant.LoginAdminUser);

		String DBPath;
		try {
			String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
			String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
			String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
			String port = (String) SystemConfig.getValue(FtpConstant.PORT);
			String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
			String module = (String) SystemConfig.getValue(FtpConstant.FTP_FILE_TEM_PATH);
			String fileName = file.getOriginalFilename();// 获取文件名
			String imageSuffix=fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			if(StringUtils.isNotEmpty(fileName) && "".equals(imageSuffix)){
				 log.warn("fileUpload----service:"+"上传文件格式不正确文件名："+fileName+",支持格式为jpg,png");
				throw new FileUploadException("上传文件格式不正确文件名："+fileName);
			}
			long size = file.getSize();
			if (size == 0) {
				throw new FileUploadException("上传文件为空文件");
			}	
			
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
			DBPath = "";
			
	//		FtpUtil ftp = null;
			module = "kindergarten";
			if (StringUtils.isNotEmpty(module)) {
				if (StringUtils.isNotEmpty(fileName)) {
					String unique = String.valueOf(System.currentTimeMillis()) + new Random().nextInt(10);
					fileName = unique+"." + imageSuffix;
					DBPath = "/" + module+"/"+fileName; //    不能这个路径/upload/wod
	//				ftp=new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
					boolean flag = false;
					try {
		//				ftp.login();
						log.info("fileUpload("+module+","+file+","+SystemConfig.getValue("pic.needSmall.module.filetype")+") -  start");
			//			flag = ftp.upload(file.getInputStream(), path + DBPath);
						flag = FileUtil.writeToLocal(path + DBPath, file.getInputStream());
						int category = 1;
						//判断文件是否为视频
						if(isVedioFile(imageSuffix)){
							category = 2;
						}
						
						KindergartenAlbum album = new KindergartenAlbum();
						String currentClass = null;
						Integer gradeId  = null;
						Integer kindergartenId  = null;
						if(type == 1){
							gradeId =Integer.parseInt(ownerId);
							//班级
							KindergartenGrade grade = kindergartenGradeMapper.selectByPrimaryKey(Integer.parseInt(ownerId));
							album.setShcoolId(grade.getKindergartenId());
							album.setGradeId(Integer.parseInt(ownerId));
							currentClass =  GradeNameUtil.getGradeName(grade);
							kindergartenId = grade.getKindergartenId();
							
						}else if(type == 2){
							//学生
							KindergartenStudent student = kindergartenStudentMapper.selectByPrimaryKey(Integer.parseInt(ownerId));
							album.setShcoolId( student.getKindergartenId());
							album.setGradeId(student.getGradeId());
							album.setStudent(Integer.parseInt(ownerId));
							KindergartenGrade grade = kindergartenGradeMapper.selectByPrimaryKey(student.getGradeId());
							currentClass =  GradeNameUtil.getGradeName(grade);
							
							gradeId = student.getGradeId();
							kindergartenId = grade.getKindergartenId();
						}
						
						//查询是否已经有相册生成
						album.setType(type);
						album.setCurrentGradeName(currentClass);
						List<KindergartenAlbum> albumList = kindergartenAlbumMapper.selectByCondition(album);
						if(albumList == null || albumList.size() == 0){
							album.setAlbumDesc("");
							album.setAlbumName(currentClass);
							album.setAlbumUrl(DBPath);
							album.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
							album.setCreateUser(userExtForm.getAccount());
							kindergartenAlbumMapper.insertSelective(album);
						}else{
							album = albumList.get(0);
						}
						if(cate == 1){
							
							KindergartenPhoto photo = new KindergartenPhoto();
							photo.setCategory(category);
							photo.setCommentNum(0);
							photo.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
							photo.setCreateUser(userExtForm.getAccount());
							photo.setDigNum(0);
							photo.setOwnerId(ownerId);
							photo.setAlbumId(album.getId());
							photo.setType(type);
							photo.setGradeId(gradeId);
							photo.setKindergartenId(kindergartenId);
							if(category == 1){
								photo.setPhotoUrl(DBPath);
							}else{
								photo.setVideoUrl(DBPath);
							}
							kindergartenPhotoMapper.insertSelective(photo);
							
							//生成压缩包
							createZipPackage(album.getId(), type, ownerId, currentClass);
						}else if(cate == 2){
							//荣誉
							
							KindergartenHonor honor = new KindergartenHonor();
							honor.setCategory(category);
							honor.setCommentNum(0);
							honor.setCreateTime(DateUtil.dateFromatYYYYMMddHHmmss(new Date()));
							honor.setCreateUser(userExtForm.getAccount());
							honor.setDigNum(0);
							honor.setOwnerId(ownerId);
							honor.setAlbumId(album.getId());
							honor.setType(type);
							honor.setGradeId(gradeId);
							honor.setPhotoUrl(DBPath);
							honor.setKindergartenId(kindergartenId);
							kindergartenHonorMapper.insertSelective(honor);
						}
						
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
						throw new FileUploadException("FTP上传文件出错");
					} finally{
				//		ftp.closeServer();
					}
					if (!flag) {
						log.error("FTP文件上传失败");
						throw new IOException("FTP文件上传失败");
					}
				} else {
					// 当文件上传的文本框为空时，对应返回的路径为空。
					DBPath = "";
				}
			}
			log.debug("fileUpload("+module+","+file+") - end");
			JSONObject result = new JSONObject();
			result.put("success", true);
			result.put("url", DBPath);
			result.put("type", type);
			return result.toString();
		} catch (Exception e) {
		
			e.printStackTrace();
			JSONObject result = new JSONObject();
			result.put("success", false);
			result.put("url", "");
			return result.toString();
		}
	}
	private void createZipPackage(final Integer albumId, final Integer type, final String ownerId, final String currentClass) {
		new Thread(){

			@Override
			public void run() {
				KindergartenPhoto photo = new KindergartenPhoto();
			//	photo.setType(type);
			//	photo.setOwnerId(ownerId);
				photo.setAlbumId(albumId);
				List<KindergartenPhoto> photoList = kindergartenPhotoMapper.selectByCondition(photo);
				List<String> urlList = new ArrayList<String>();
				for(KindergartenPhoto p : photoList){
					if(p.getCategory() == 1){
						urlList.add(Constant.path + p.getPhotoUrl());
					}else if(p.getCategory() == 2){
						urlList.add(Constant.path +p.getVideoUrl());
					}
				}
				String to ="/kindergarten/Album/"+ albumId +".zip" ;
				String targetUrl = Constant.resPath + to ;
				String[] arr = {};
				delOldZipFile(to);
				FileZipUtil.zip(urlList.toArray(arr), targetUrl);
				
				KindergartenAlbum album = new KindergartenAlbum();
				album.setId(albumId);
				album.setDownloadUrl(to);
				album.setDownloadSecret(RandomGUID.getRandomString(4));
				kindergartenAlbumMapper.updateByPrimaryKeySelective(album);
			}

		}.start();
		
	}
	private void delOldZipFile(String remoteFilePath) {
		/*String ftpAddress = (String) SystemConfig.getValue(FtpConstant.FTP_ADDRESS);
		String username = (String) SystemConfig.getValue(FtpConstant.USERNAME);
		String password = (String) SystemConfig.getValue(FtpConstant.PASSWORD);
		String port = (String) SystemConfig.getValue(FtpConstant.PORT);
		String path = (String) SystemConfig.getValue(FtpConstant.FTP_PATH);
		FtpUtil ftp = new FtpUtil(ftpAddress, Integer.parseInt(port), username, password);
		try {
			ftp.login();
			ftp.delFile(remoteFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		File file = new File(remoteFilePath);
		file.delete();
		
	}
	
    /**
     * 根据文件后缀名判断 文件是否是视频文件
     * @param fileName 文件名
     * @return 是否是视频文件
     */
    public static boolean isVedioFile(String imageSuff){
    	
    	String video[] = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
		for (int i = 0; i < video.length; i++) {
			if (video[i].equalsIgnoreCase(imageSuff)) {
				return true;
			}
		}
    	
        return false;
    }

	
}
