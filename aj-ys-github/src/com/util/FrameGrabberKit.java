package com.util;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.Java2DFrameConverter;

import org.bytedeco.javacv.OpenCVFrameConverter;

public class FrameGrabberKit {

	public static void main(String[] args) throws Exception {

		randomGrabberFFmpegImage("/Users/zoujiang/Downloads/video8.mp4", "/Users/zoujiang/Downloads/", "111");
	}

	public static void randomGrabberFFmpegImage(String filePath, String targerFilePath, String targetFileName)
            throws Exception {

        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
        ff.start();
        String rotate =ff.getVideoMetadata("rotate");
        Frame f;
        int i = 0;
        while (i <1) {
            f =ff.grabImage();
            IplImage src = null;
            if(null !=rotate &&rotate.length() > 1) {
                OpenCVFrameConverter.ToIplImage converter =new OpenCVFrameConverter.ToIplImage();
                    src =converter.convert(f);
                    f =converter.convert(rotate(src, Integer.valueOf(rotate)));
                }
                doExecuteFrame(f,targerFilePath,targetFileName);
            i++;
        }

        ff.stop();

    }

	/*
	 * 旋转角度的
	 */
	public static IplImage rotate(IplImage src, int angle) {
		IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
		opencv_core.cvTranspose(src, img);
		opencv_core.cvFlip(img, img, angle);
		return img;
	}

	public static void doExecuteFrame(Frame f, String targerFilePath, String targetFileName) {

        if (null ==f ||null ==f.image) {
            return;
        }
        Java2DFrameConverter converter =new Java2DFrameConverter();
        String FileName =targerFilePath + File.separator +targetFileName;
        BufferedImage bi =converter.getBufferedImage(f);
        System.out.println("width:" + bi.getWidth());
        System.out.println("height:" + bi.getHeight());
        File output =new File(FileName);
        try {
            ImageIO.write(bi,"jpg",output);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}