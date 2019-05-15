package com.qinhan.videoblog.web.common;

import org.apache.oro.text.regex.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessVideo {

	/**
	 * 对视频转码并且截屏，然后存放到指定wei
	 * 
	 * @param ffmpegPath   我们存放ffmpeg转码工具的路径
	 * @param upFilePath   我们存放上传文件路径
	 * @param codeFilePath 转码后文件存放路径
	 * @param mediaPicPath 视频截图存放路径
	 * @return
	 */
	public static boolean executeCodes(String ffmpegPath, String upFilePath, String codeFilePath, String mediaPicPath) {
		// 创建List集合保存视频文件为flv格式的命令
		// 这里的参数之后处理，根据每个上传视频的不同来指定，尽量做到不丢失吧。
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath);
		convert.add("-i");
		convert.add(upFilePath);// 要转换格式的视频文件的路径
		convert.add("-qscale");// 转换的质量
		convert.add("6");
		convert.add("-ab");// 设置音频码率
		convert.add("64");
		convert.add("-ac");// 设置声道数
		convert.add("2");
		convert.add("-ar");// 设置声音的采样频率
		convert.add("22050");
		convert.add("-r");// 设置帧频
		convert.add("24");
		convert.add("-y");// 表示会覆盖已经存在的文件
		convert.add(codeFilePath);// 转码后的文件存放路径

//		 saveMediaPic(ffmpegPath, upFilePath, mediaPicPath);

		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {
			// 通过builder来执行cmd命令
			builder.command(convert);
			builder.redirectErrorStream(true);
			builder.start();

		} catch (Exception e) {
			mark = false;
			System.out.println(e);
			e.printStackTrace();
		}
		return mark;
	}

	/**
	 * 从通过ffmpeg将上传文件抽帧并且将关键帧存放到指定目录中,然后返回该文键路径
	 * 
	 * @param ffmpegPath
	 * @param upFilePath
	 * @param mediaPicPath
	 * @return
	 */
	public static boolean saveMediaPic(String ffmpegPath, String upFilePath, String mediaPicPath) {
		System.out.println("开始提取图片:上传文件位置:"+upFilePath+"->"+"图片保存路径"+mediaPicPath);
		// 创建一个List集合保存从视频截取图片的命令 ---从第三秒处截取一个图片
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(upFilePath);
		cutpic.add("-y");
		cutpic.add("-ss");
		cutpic.add("3");
		cutpic.add("-t");
		cutpic.add("0.001");
		cutpic.add("-s");
		cutpic.add("1600x900");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add(mediaPicPath);
		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {
			builder.command(cutpic);
			builder.redirectErrorStream(true);
			// 如果该属性为true，则任何通过此对象的start()方法启动的后续子进程生成的错误输出都将与标准输出合并
			// 然后通过Process.getInputStream()来读取相应的错误消息和相应输出
			builder.start();
		} catch (Exception e) {
			mark = false;
			System.out.println(e);
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 补充做法
	 * 
	 * @param videoPath
	 * @param videoPath
	 */
	public static void cutPic(String ffmpegPath, String videoPath, String mediaPicPath) {
		System.out.println("开始提取图片:上传文件位置:"+videoPath+"->"+"图片保存路径"+mediaPicPath);
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(videoPath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
		cutpic.add("image2");
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("4cif");
		cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		cutpic.add("17"); // 添加起始时间为第17秒
		cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cutpic.add("0.001"); // 添加持续时间为1毫秒
		cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cutpic.add("800*280"); // 添加截取的图片大小为350*240
		cutpic.add(mediaPicPath); // 添加截取的图片的保存路径,包含文件名

		ProcessBuilder builder = new ProcessBuilder();
		try {
			builder.command(cutpic);
			// 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
			// 因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
			builder.redirectErrorStream(true);
			builder.start();

		} catch (Exception e) {
			throw new RuntimeException("提取关键帧失败", e);
		}
	}

	/**
	 * 获取视频时长
	 * 
	 * @param upFilePath
	 * @param ffmpegPath
	 */
	public static String getVideoInfo(String upFilePath, String ffmpegPath) {
		String result = processVideo(upFilePath, ffmpegPath);
		String duration = "";
		PatternCompiler compiler = new Perl5Compiler();
		try { // 要从视频信息中提取的信息的正则表达式
			String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";

			Pattern patternDuration = compiler.compile(regexDuration, Perl5Compiler.CASE_INSENSITIVE_MASK);
			PatternMatcher matcherDuration = new Perl5Matcher();
			if (matcherDuration.contains(result, patternDuration)) {
				org.apache.oro.text.regex.MatchResult re = matcherDuration.getMatch();
				duration = re.group(1);
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return duration;
	}

	/**
	 * 处理
	 * 
	 * @param inputPath
	 * @return
	 */
	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private static String processVideo(String inputPath, String ffmpegPath) {
		// 通过command调用ffmpeg处理视频，并返回视频结果字符串
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegPath);// 这里我已经将ffmpeg添加到环境变量中
		commend.add("-i");
		commend.add(inputPath);

		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.redirectErrorStream(true);
			Process p = builder.start();//包含输出结果流信息

			// 1. start
			BufferedReader buf = null; // 保存ffmpeg的输出结果流
			String line = null;
			// read the standard output

			buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

			StringBuffer sb = new StringBuffer();
			while ((line = buf.readLine()) != null) {
//				System.out.println(line);
				sb.append(line);
				continue;
			}
			p.waitFor();// 这里线程阻塞，将等待外部转换进程运行成功运行结束后，才往下执行
			// 1. end
			return sb.toString();
		} catch (Exception e) {
			// System.out.println(e);
			return null;
		}
	}

	/**
	 * 裁剪图片
	 */
	/*public static boolean cutImg(String srcPath, String toPath) {
		try {
			BufferedImage img = ImageIO.read(new File(srcPath));

			BufferedImage newImage = Scalr.resize(img, Scalr.Method.SPEED, 50, 50);

			ImageIO.write(newImage, "JPEG", new File(toPath));

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}*/

}
