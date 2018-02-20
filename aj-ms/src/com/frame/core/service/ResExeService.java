package com.frame.core.service;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.frame.core.vo.PreviewImageVo;
import com.frame.core.vo.PreviewResVo;
/**
 *   获取图片业务接口
 * @author yt
 *
 */
public interface ResExeService {
	/**
	 * 获取资源流处理
	 * @param resVo 路径(eg:/vote/vote4.mp3)
	 * @return
	 */
  public  OutputStream getResAsByte(PreviewResVo resVo,HttpServletResponse response)throws Exception ;
  public  InputStream getInputResAsByte(PreviewResVo resVo)throws Exception ;
  public boolean downFile2Local(PreviewResVo previewResVo,String localAddr) throws Exception;
public Boolean downZipFile2Local(PreviewResVo previewExeVo, String mp4Path);

}
