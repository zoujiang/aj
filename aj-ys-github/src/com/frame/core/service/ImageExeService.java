package com.frame.core.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.frame.core.vo.PreviewImageVo;
/**
 *   获取图片业务接口
 * @author yt
 *
 */
public interface ImageExeService {
	/**
	 * 获取图片流处理
	 * @param imagePath 图片路径(eg:/vote/vote4.jpg)
	 * @return
	 */
  public  OutputStream getImageAsByte( PreviewImageVo previewImageVo,HttpServletResponse response)throws Exception ;
}
