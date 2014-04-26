package com.qmx.ioutils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.qmx.temp.AnimatedGifEncoder;
import com.qmx.temp.GifDecoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印、补白等
 * compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
 *
 * @author Administrator
 */
public class ImageUtils {
	private static final Logger log = Logger.getLogger(ImageUtils.class);
	private static final Dimension d = Toolkit.getDefaultToolkit()
			.getScreenSize();
	public static Map<String, String> types = new HashMap<String, String>();
	/**
	 * 几种常见的图片格式
	 */
	public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
	public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
	public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
	public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
	public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
	public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

	private static File file = null; // 文件对象
	private static String inputDir; // 输入图路径
	private static String outputDir; // 输出图路径
	private static String inputFileName; // 输入图文件名
	private static String outputFileName; // 输出图文件名
	private static int outputWidth = 100; // 默认输出图片宽
	private static int outputHeight = 100; // 默认输出图片高
	private static boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)

	private static int width;
	private static int height;
	private static int scaleWidth;
	private static double support = (double) 3.0;
	private static double PI = (double) 3.14159265358978;
	private static double[] contrib;
	private static double[] normContrib;
	private static double[] tmpContrib;
	private static int nDots;
	private static int nHalfDots;

	private BufferedImage bufferedImage;
	private String fileName;

	static {
		types.put("gif", "gif");
		types.put("jpg", "jpg");
		types.put("jpeg", "jpg");
		types.put("png", "png");
	}

	public static String formatForExtension(String extension) {
		final String type = types.get(extension);
		if (type == null) {
			return IMAGE_TYPE_PNG;
		}
		return type;
	}

	public static String formatForFilename(String fileName) {
		final int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex < 0) {
			return IMAGE_TYPE_PNG;
		}
		final String ext = fileName.substring(dotIndex + 1);
		return formatForExtension(ext);
	}

	/**
	 * 缩放图像（按比例缩放）
	 *
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public final static void scale(String srcImageFile, String result,
			int scale, boolean flag) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件
			int width = src.getWidth(); // 得到源图宽
			int height = src.getHeight(); // 得到源图长
			if (flag) {// 放大
				width = width * scale;
				height = height * scale;
			} else {// 缩小
				width = width / scale;
				height = height / scale;
			}
			Image image = src.getScaledInstance(width, height,
					Image.SCALE_DEFAULT);
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缩放图像（按高度和宽度缩放）
	 *
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param height
	 *            缩放后的高度
	 * @param width
	 *            缩放后的宽度
	 * @param bb
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 */
	public final static void scale(String srcImageFile, String result,
			int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {// 补白
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 1、下载ImageMagick-6.6.2-9-Q16-windows-dll.exe并安装，
	// 安装后系统会自动在环境变量path中添加值C:\Program Files\ImageMagick-6.6.2-Q16。
	//
	// 2、将ImageMagick-6.6.2-9-Q16-windows-dll安装目录下的所有.dll文件都拷到C:\WINDOWS\system32下。
	//
	// 3、下载jmagick-win-6.3.9-Q16.zip并解压，解压后，
	// 将文件jmagick.dll拷到jdk的bin目录下。
	// 将jmagick.jar复制到所使用项目的\WEB-INF\lib目录下。
	//
	// 4、在java脚本中加上一句：static{System.setProperty("jmagick.systemclassloader","no");}

	// 5、代码：该方法为将图片按比率高清压缩为150*120范围内的缩略图，在上传函数中调用。
	// 参数：filePath为上传图片的路径，toPath为压缩后图片的路径。

	/**
	 * 缩放图像（按比例缩放）
	 *
	 * @param srcImageFile
	 *            源图像文件地址
	 * @param result
	 *            缩放后的图像地址
	 * @param scale
	 *            缩放比例
	 * @param flag
	 *            缩放选择:true 放大; false 缩小;
	 */
	public final static boolean scale(String srcImageFile, String result,
			int scale) throws Exception {
		System.setProperty("jmagick.systemclassloader", "no");
		boolean sf = false;
		ImageInfo info = null;
		MagickImage image = null;
		Dimension imageDim = null;
		MagickImage scaled = null;
		try {
			info = new ImageInfo(srcImageFile);
			image = new MagickImage(info);
			imageDim = image.getDimension();
			float width = imageDim.width;
			float height = imageDim.height;
			float bl = 1;
			float bl_w = width / scale;
			float bl_h = height / scale;
			if (bl_w >= bl_h) {
				bl = bl_w;
			} else {
				bl = bl_h;
			}
			width = width / bl;
			height = height / bl;
			int new_width = (int) width;
			int new_height = (int) height;
			scaled = image.scaleImage(new_width, new_height);
			scaled.setFileName(result);
			scaled.writeImage(info);
			sf = true;
		} catch (Exception ex) {
			sf = false;
		} finally {
			if (scaled != null) {
				scaled.destroyImages();
			}
		}
		return sf;
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 *
	 * @param srcImageFile
	 *            源图像地址
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 */
	public final static void cut(String srcImageFile, String result, int x,
			int y, int width, int height) {
		try {
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width,
						height);
				Image img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				// 输出为文件
				ImageIO.write(tag, "JPEG", new File(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的行数和列数）
	 *
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param rows
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param cols
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 */
	public final static void cut2(String srcImageFile, String descDir,
			int rows, int cols) {
		try {
			if (rows <= 0 || rows > 20)
				rows = 2; // 切片行数
			if (cols <= 0 || cols > 20)
				cols = 2; // 切片列数
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int destWidth = srcWidth; // 每张切片的宽度
				int destHeight = srcHeight; // 每张切片的高度
				// 计算切片的宽度和高度
				if (srcWidth % cols == 0) {
					destWidth = srcWidth / cols;
				} else {
					destWidth = (int) Math.floor(srcWidth / cols) + 1;
				}
				if (srcHeight % rows == 0) {
					destHeight = srcHeight / rows;
				} else {
					destHeight = (int) Math.floor(srcWidth / rows) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像切割（指定切片的宽度和高度）
	 *
	 * @param srcImageFile
	 *            源图像地址
	 * @param descDir
	 *            切片目标文件夹
	 * @param destWidth
	 *            目标切片宽度。默认200
	 * @param destHeight
	 *            目标切片高度。默认150
	 */
	public final static void cut3(String srcImageFile, String descDir,
			int destWidth, int destHeight) {
		try {
			if (destWidth <= 0)
				destWidth = 200; // 切片宽度
			if (destHeight <= 0)
				destHeight = 150; // 切片高度
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image img;
				ImageFilter cropFilter;
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * destWidth, i
								* destHeight, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(),
										cropFilter));
						BufferedImage tag = new BufferedImage(destWidth,
								destHeight, BufferedImage.TYPE_INT_RGB);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "_r" + i
								+ "_c" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
	 *
	 * @param srcImageFile
	 *            源图像地址
	 * @param formatName
	 *            包含格式非正式名称的 String：如JPG、JPEG、GIF等
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void convert(String srcImageFile, String formatName,
			String destImageFile) {
		try {
			File f = new File(srcImageFile);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, formatName, new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 *
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 */
	public final static void gray(String srcImageFile, String destImageFile) {
		try {
			BufferedImage src = ImageIO.read(new File(srcImageFile));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(destImageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加文字水印
	 *
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            水印的字体名称
	 * @param fontStyle
	 *            水印的字体样式
	 * @param color
	 *            水印的字体颜色
	 * @param fontSize
	 *            水印的字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText,
					(width - (getLength2(pressText) * fontSize)) / 2 + x,
					(height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加图片水印
	 *
	 * @param pressImg
	 *            水印图片
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param x
	 *            修正值。 默认在中间
	 * @param y
	 *            修正值。 默认在中间
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressImage(String pressImg, String srcImageFile,
			String destImageFile, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,
					(height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加图片水印
	 *
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg
	 *            水印图片路径，如：C://myPictrue//logo.png
	 * @param x
	 *            水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public final static void pressImage(String targetImg, String waterImg,
			int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, IMAGE_TYPE_JPG, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加文字水印
	 *
	 * @param targetImg
	 *            目标图片路径，如：C://myPictrue//1.jpg
	 * @param pressText
	 *            水印文字， 如：中国证券网
	 * @param fontName
	 *            字体名称， 如：宋体
	 * @param fontStyle
	 *            字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
	 * @param fontSize
	 *            字体大小，单位为像素
	 * @param color
	 *            字体颜色
	 * @param x
	 *            水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y
	 *            水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha
	 *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public static void pressText(String targetImg, String pressText,
			String fontName, int fontStyle, int fontSize, Color color, int x,
			int y, float alpha) {
		try {
			File file = new File(targetImg);

			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setColor(color);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			int width_1 = fontSize * getLength(pressText);
			int height_1 = fontSize;
			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}

			g.drawString(pressText, x, y + height_1);
			g.dispose();
			ImageIO.write(bufferedImage, IMAGE_TYPE_JPG, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
	 *
	 * @param text
	 * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
	 */
	public static int getLength(String text) {
		int textLength = text.length();
		int length = textLength;
		for (int i = 0; i < textLength; i++) {
			if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
				length++;
			}
		}
		return (length % 2 == 0) ? length / 2 : length / 2 + 1;
	}

	/**
	 * 计算text的长度（一个中文算两个字符）
	 *
	 * @param text
	 * @return
	 */
	public final static int getLength2(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}

	/**
	 * 图片缩放
	 *
	 * @param filePath
	 *            图片路径
	 * @param height
	 *            高度
	 * @param width
	 *            宽度
	 * @param bb
	 *            比例不对时是否需要补白
	 */
	public static void resize(String filePath, int height, int width, boolean bb) {
		try {
			double ratio = 0; // 缩放比例
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height,
					BufferedImage.SCALE_SMOOTH);
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue()
							/ bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(
						AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "jpg", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param inputFile
	 * @param outputPicName
	 *            文件全名，后缀为.jpg
	 * @param max
	 * @author wenc
	 */
	public static void zoomPicture(File inputFile, String outputPicName,
			double max) {
		double maxLimit = max;
		double ratio = 1.0;
		try {
			BufferedImage Bi = ImageIO.read(inputFile);
			if ((Bi.getHeight() > maxLimit) || (Bi.getWidth() > maxLimit)) {
				if (Bi.getHeight() > Bi.getWidth())
					ratio = maxLimit / Bi.getHeight();
				else
					ratio = maxLimit / Bi.getWidth();
			}
			int widthdist = (int) Math.floor(Bi.getWidth() * ratio), heightdist = (int) Math
					.floor(Bi.getHeight() * ratio);

			BufferedImage tag = new BufferedImage(widthdist, heightdist,
					BufferedImage.TYPE_INT_RGB);

			tag.getGraphics().drawImage(
					Bi.getScaledInstance(widthdist, heightdist,
							BufferedImage.SCALE_SMOOTH), 0, 0, null);

			File littleFile = new File(outputPicName);
			ImageIO.write(tag, "JPEG", littleFile);
		} catch (Exception ex) {
			log.error("图片写入错误" + ex.getMessage());
		}
	}

	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            需要缩小的倍数，缩小2倍为原来的1/2 ，这个数值越大，返回的图片越小
	 * @return 返回处理后的图像
	 */
	public BufferedImage resizeImage(BufferedImage im, float resizeTimes) {
		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();

		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);

		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight,
				BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(
				im.getScaledInstance(toWidth, toHeight,
						java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}

	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	public static BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();

		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);

		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight,
				BufferedImage.TYPE_INT_RGB);

		result.getGraphics().drawImage(
				im.getScaledInstance(toWidth, toHeight,
						java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}

	/**
	 *
	 * @param path
	 *            要转化的图像的文件夹,就是存放图像的文件夹路径
	 * @param type
	 *            图片的后缀名组成的数组
	 * @return
	 */
	public static List<BufferedImage> getImageList(String path, String[] type)
			throws IOException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (String s : type) {
			map.put(s, true);
		}
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		File[] fileList = new File(path).listFiles();
		for (File f : fileList) {
			if (f.length() == 0)
				continue;
			if (map.get(getExtension(f.getName())) == null)
				continue;
			result.add(javax.imageio.ImageIO.read(f));
		}
		return result;
	}

	/**
	 * 把图片写到磁盘上
	 *
	 * @param im
	 * @param path
	 *            eg: C://home// 图片写入的文件夹地址
	 * @param fileName
	 *            DCM1987.jpg 写入图片的名字
	 * @return
	 */
	public static boolean writeToDisk(BufferedImage im, String path,
			String fileName) {
		File f = new File(path + fileName);
		String fileType = getExtension(fileName);
		if (fileType == null)
			return false;
		try {
			ImageIO.write(im, fileType, f);
			im.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@SuppressWarnings("restriction")
	public static boolean writeHighQuality(BufferedImage im, String fileFullPath) {
		try {
			/* 输出到文件流 */
			FileOutputStream newimage = new FileOutputStream(fileFullPath
					+ System.currentTimeMillis() + ".jpg");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			/* 压缩质量 */
			jep.setQuality(1f, true);
			encoder.encode(im, jep);
			/* 近JPEG编码 */
			newimage.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 返回文件的文件后缀名
	 *
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		try {
			return fileName.split("\\.")[fileName.split("\\.").length - 1];
		} catch (Exception e) {
			return null;
		}
	}

	// -=====================================

	@SuppressWarnings("restriction")
	public static boolean StringToImage(String imgStr, String imgFilePath) {
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) {// 图像数据为空
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}

			// 生成jpeg图片
			File file = new File(imgFilePath);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
			fos.close();
			log.info("Decoding the picture Success");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("restriction")
	public static String GetImageStr(String imgFilePath) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgFilePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * @param imageURL
	 * @return
	 * @throws Exception
	 * @description 描述:
	 * @version 1.0
	 * @author cdd
	 * @update 2012-6-3 上午11:28:44
	 */
	public static String imageToString(String imageURL) throws Exception {

		BASE64Encoder encoder = new BASE64Encoder();
		StringBuffer picBuffer = new StringBuffer();

		InputStream input = new FileInputStream(new File(imageURL));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] temp = new byte[1024];
		for (int len = input.read(temp); len != -1; len = input.read(temp)) {
			out.write(temp, 0, len);
			picBuffer.append(encoder.encode(out.toByteArray()));
			out.reset();
		}

		// log.info(picBuffer.toString());
		// log.info("Encoding the picture Success");
		return picBuffer.toString();
	}

	// ======================================
	// 图片缩放
	public static void Jwh(String URLPath, String srcImage, String DestImage,
			int WLen, int HLen) {

		MagickImage scaled = null;
		Rectangle rect = null;
		int x = 0;
		int y = 0;
		int lw = 0;
		int lh = 0;

		try {

			ImageInfo info = new ImageInfo(URLPath + srcImage);

			MagickImage image = new MagickImage(info);

			// 取长宽

			Dimension dim = image.getDimension();

			double wImage = dim.getWidth();

			double hImage = dim.getHeight();

			if (HLen == 0) { // 正常缩小

				Boolean bWBig = wImage > hImage ? true : false;

				if (bWBig)

				{// 长大过高

					hImage = WLen * (hImage / wImage);

					wImage = WLen;

				}

				else

				{// 反之

					wImage = WLen * (wImage / hImage);

					hImage = WLen;

				}

			} else {// 剪切缩小

				Boolean bWBig = wImage - WLen < hImage - HLen ? true : false;

				if (bWBig)

				{// 长大过高

					hImage = WLen * (hImage / wImage);

					wImage = WLen;

				}

				else

				{// 反之

					wImage = HLen * (wImage / hImage);

					hImage = HLen;

				}

			}

			lw = (int) wImage;

			lh = (int) hImage;

			// 输出

			scaled = image.scaleImage(lw, lh);

			if (HLen > 0) {// 剪切缩小必须是ＪＰＧ格式

				x = (lw - WLen) / 2;

				y = (lh - HLen) / 2;

				lw = lw - (lw - WLen);

				lh = lh - (lh - HLen);

				rect = new Rectangle(x, y, lw, lh);

				scaled = scaled.cropImage(rect);

			}

			scaled.setFileName(URLPath + DestImage);

			scaled.writeImage(info);

			scaled.destroyImages();

		} catch (MagickApiException ex) {

		} catch (MagickException ex) {

		} finally {

			scaled.destroyImages();

		}

	}

	// ==========================================
	// 1 多张jpg图合成gif动画

	/**
	 * 把多张jpg图片合成一张
	 *
	 * @param pic
	 *            String[] 多个jpg文件名 包含路径
	 * @param newPic
	 *            String 生成的gif文件名 包含路径
	 */
	private synchronized void jpgToGif(String pic[], String newPic) {
		try {
			AnimatedGifEncoder e = new AnimatedGifEncoder(); // 网上可以找到此类
			e.setRepeat(0);
			e.start(newPic);
			BufferedImage src[] = new BufferedImage[pic.length];
			for (int i = 0; i < src.length; i++) {
				e.setDelay(200); // 设置播放的延迟时间
				src[i] = ImageIO.read(new File(pic[i])); // 读入需要播放的jpg文件
				e.addFrame(src[i]); // 添加到帧中
			}
			e.finish();
		} catch (Exception e) {
			System.out.println("jpgToGif Failed:");
			e.printStackTrace();
		}
	}

	// 2 gif动画分解成多张jpg

	/**
	 * 把gif图片按帧拆分成jpg图片
	 *
	 * @param gifName
	 *            String 小gif图片(路径+名称)
	 * @param path
	 *            String 生成小jpg图片的路径
	 * @return String[] 返回生成小jpg图片的名称
	 */
	private synchronized String[] splitGif(String gifName, String path) {
		try {
			GifDecoder decoder = new GifDecoder();
			decoder.read(gifName);
			int n = decoder.getFrameCount(); // 得到frame的个数
			String[] subPic = new String[n];
			// String tag = this.getTag();
			for (int i = 0; i < n; i++) {
				BufferedImage frame = decoder.getFrame(i); // 得到帧
				// int delay = decoder.getDelay(i); //得到延迟时间
				// 生成小的JPG文件
				subPic[i] = path + String.valueOf(i) + ".jpg";
				FileOutputStream out = new FileOutputStream(subPic[i]);
				ImageIO.write(frame, "jpeg", out);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(frame); // 存盘
				out.flush();
				out.close();
			}
			return subPic;
		} catch (Exception e) {
			System.out.println("splitGif Failed!");
			e.printStackTrace();
			return null;
		}
	}

	// 3 根据提供的文字生成jpg图片

	/**
	 * 根据提供的文字生成jpg图片
	 *
	 * @param s
	 *            String 文字
	 * @param smallWidth
	 *            int 每个字的宽度和高度是一样的
	 * @param bgcolor
	 *            Color 背景色
	 * @param fontcolor
	 *            Color 字色
	 * @param fontPath
	 *            String 字体文件
	 * @param jpgname
	 *            String jpg图片名
	 * @return
	 */
	private void createJpgByFont(String s, int smallWidth, Color bgcolor,
			Color fontcolor, String fontPath, String jpgname) {
		try { // 宽度 高度
			BufferedImage bimage = new BufferedImage(s.length() * smallWidth,
					smallWidth, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bimage.createGraphics();
			g.setColor(bgcolor); // 背景色
			g.fillRect(0, 0, smallWidth, smallWidth); // 画一个矩形
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON); // 去除锯齿(当设置的字体过大的时候,会出现锯齿)
			g.setColor(fontcolor); // 字的颜色
			File file = new File(fontPath); // 字体文件
			Font font = Font.createFont(Font.TRUETYPE_FONT, file); // 根据字体文件所在位置,创建新的字体对象(此语句在jdk1.5下面才支持)
			g.setFont(font.deriveFont((float) smallWidth)); // font.deriveFont(float
			// f)复制当前 Font
			// 对象并应用新设置字体的大小
			g.drawString(s, 0, smallWidth); // 在指定坐标除添加文字
			g.dispose();
			FileOutputStream out = new FileOutputStream(jpgname); // 指定输出文件
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(50f, true);
			encoder.encode(bimage, param); // 存盘
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("createJpgByFont Failed!");
			e.printStackTrace();
		}
	}

	// 4 多张小jpg图合成一张大JPG图，在这里对大图只作宽度限制，不做高度限制

	/**
	 * 将多个小图片合成一张大jpg图 (小的jpg图片按照行列顺序平铺)
	 *
	 * @param smallJPG
	 *            ArrayList 一组小的jpg图片
	 * @param bigWidth
	 *            int 大图宽度
	 * @param smallWidth
	 *            int 单个文字生成的小图的宽度和高度是一致的
	 * @return
	 */
	private void createBigJPG(ArrayList smallJPG, int bigWidth, int smallWidth,
			int smallHeigh, Color bgColor, String picName) {

		try {
			if (bigWidth < smallWidth) // 如果大图片的高度比小图片的高度还小 直接返回
				return;
			int colCount = bigWidth / smallWidth; // 每行放置的字数
			int leftMargin = (int) ((bigWidth - colCount * smallWidth) / 2f); // 左边距
			int rowCount = smallJPG.size(); // 小图行数
			int setWidth = bigWidth; // 每列中间不留空隙，只留左右边距
			int setHeight = smallWidth * rowCount;
			// 按照大图片宽高绘制一个背景图片
			BufferedImage bufImage = new BufferedImage(setWidth, setHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImage.createGraphics();
			g.setColor(bgColor); // 背景的颜色
			g.fillRect(0, 0, setWidth, setHeight);
			int y = 0; // 纵坐标
			for (int i = 0; i < rowCount; i++) { // 遍历每行
				ArrayList col = (ArrayList) (smallJPG.get(i));
				int x = leftMargin; // 横坐标 可能会出现左边距
				for (int j = 0; j < col.size(); j++) {
					String jpgname = (String) (col.get(j));
					ImageIcon icon = new ImageIcon(jpgname);
					Image img = icon.getImage();
					int imgWidth = img.getHeight(null);
					g.drawImage(img, x, y, null);
					x += imgWidth;
				}
				y += (smallWidth);
			}
			g.dispose();
			FileOutputStream out = new FileOutputStream(picName); // 指定输出文件
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); // 设置文件格式
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufImage); // 从图片缓冲中读取
			param.setQuality(50f, true);
			encoder.encode(bufImage, param); // 存盘
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("createBigJPG Failed!");
			e.printStackTrace();
		}
	}

	// 注：
	//
	// (1)AnimatedGifEncoder和GifDecoder，以及这两个类涉及到的相关类，在网上搜索一下就可以找到。
	//
	// (2)在linux系统下，如果你想支持更多系统外的字体，使用下面两句话，可以不用为系统添加字体，直接把字体文件拷贝到相应位置即可，但是需要jdk1.5环境。
	// File file = new File(fontPath); //字体文件
	// Font font = Font.createFont(Font.TRUETYPE_FONT, file);
	// //根据字体文件所在位置,创建新的字体对象
	// 如果是jdk1.5以下版本则需要为系统添加字体，因为createFont(int fontFormat, File fontFile)
	// 这个方法，是从1.5才开始有的。
	//
	// (3)g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	// 我在测试中发现，当设置的字体过大的时候，会出现很明星的锯齿，后来在网上找到了这个解决方法。
	//
	// (4)有了以上几个方法，就可以做出更好看的闪信了。我也是因为需求才写下这些方法的，美工做了一些热门词汇的gif图片，在短信转彩信遇到这些词汇时，要使用提供的图片替换文字。

	/***********************************************************************
	 * 该JavaBean可以直接在其他Java应用程序中调用，实现屏幕的"拍照" This JavaBean is used to snapshot
	 * the GUI in a Java application! You can embeded it in to your java
	 * application source code, and us it to snapshot the right GUI of the
	 * application 对屏幕进行拍照 snapShot the Gui once
	 * 默认的文件前缀为"GuiCamera_系统当前时间"，文件格式为PNG格式 The default construct will use the
	 * default Image file surname "GuiCamera_SystemCurrentTime", and default
	 * image format "png"
	 *
	 * @param filePath
	 *            文件的存储目录 默认为E:\logs\
	 * @param fileName
	 *            文件的前缀 the surname of the snapshot file
	 * @param imageFormat
	 *            图像文件的格式 the format of the image file, it can be "jpg" or "png"
	 *            本构造支持JPG和PNG文件的存储
	 **********************************************************************/
	public static boolean snapShot(String filePath, String fileName,
			String imageFormat) {
		boolean flag = false;
		try {

			if (filePath == null || "".equals(filePath)) {
				filePath = "E:/logs/";
			}
			if (fileName == null || "".equals(fileName)) {
				fileName = "GuiCamera_" + System.currentTimeMillis();

			}
			if (imageFormat == null || "".equals(imageFormat)) {
				imageFormat = "png";
			}

			// 拷贝屏幕到一个BufferedImage对象screenshot
			// 这里 createScreenCapture中的4个参数就是需要截取的图形的2个顶点，也就是通过js确定的2个点
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0,
							(int) d.getWidth(), (int) d.getHeight()));
			// 根据文件前缀变量和文件格式变量，自动生成文件名
			String name = fileName + "." + imageFormat;
			File f = new File(filePath + name);
			// 将screenshot对象写入图像文件
			ImageIO.write(screenshot, imageFormat, f);
			flag = true;
		} catch (Exception e) {
			flag = false;
			System.out.println(e);
		}
		return flag;
	}

	/****************************************************************
	 * ScaleImage Begin
	 * ****************************************************************/
	// fromFileStr原图片地址,saveToFileStr生成缩略图地址,formatWideth生成图片宽度,formatHeight高度
	public void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int formatWideth, int formatHeight) throws Exception {
		BufferedImage srcImage;
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		srcImage = javax.imageio.ImageIO.read(fromFile); // construct image
		int imageWideth = srcImage.getWidth(null);
		int imageHeight = srcImage.getHeight(null);
		int changeToWideth = 0;
		int changeToHeight = 0;
		if (imageWideth > 0 && imageHeight > 0) {
			// flag=true;
			if (imageWideth / imageHeight >= formatWideth / formatHeight) {
				if (imageWideth > formatWideth) {
					changeToWideth = formatWideth;
					changeToHeight = (imageHeight * formatWideth) / imageWideth;
				} else {
					changeToWideth = imageWideth;
					changeToHeight = imageHeight;
				}
			} else {
				if (imageHeight > formatHeight) {
					changeToHeight = formatHeight;
					changeToWideth = (imageWideth * formatHeight) / imageHeight;
				} else {
					changeToWideth = imageWideth;
					changeToHeight = imageHeight;
				}
			}
		}
		srcImage = imageZoomOut(srcImage, changeToWideth, changeToHeight);
		ImageIO.write(srcImage, "JPEG", saveFile);
	}

	public BufferedImage imageZoomOut(BufferedImage srcBufferImage, int w, int h) {
		width = srcBufferImage.getWidth();
		height = srcBufferImage.getHeight();
		scaleWidth = w;
		if (DetermineResultSize(w, h) == 1) {
			return srcBufferImage;
		}
		CalContrib();
		BufferedImage pbOut = HorizontalFiltering(srcBufferImage, w);
		BufferedImage pbFinalOut = VerticalFiltering(pbOut, h);
		return pbFinalOut;
	}

	/** */
	/**
	 * 决定图像尺寸
	 */
	private int DetermineResultSize(int w, int h) {
		double scaleH, scaleV;
		scaleH = (double) w / (double) width;
		scaleV = (double) h / (double) height;
		// 需要判断一下scaleH，scaleV，不做放大操作
		if (scaleH >= 1.0 && scaleV >= 1.0) {
			return 1;
		}
		return 0;
	} // end of DetermineResultSize()

	private double Lanczos(int i, int inWidth, int outWidth, double Support) {
		double x;
		x = (double) i * (double) outWidth / (double) inWidth;
		return Math.sin(x * PI) / (x * PI) * Math.sin(x * PI / Support)
				/ (x * PI / Support);
	}

	private void CalContrib() {
		nHalfDots = (int) ((double) width * support / (double) scaleWidth);
		nDots = nHalfDots * 2 + 1;
		try {
			contrib = new double[nDots];
			normContrib = new double[nDots];
			tmpContrib = new double[nDots];
		} catch (Exception e) {
			System.out.println("init   contrib,normContrib,tmpContrib" + e);
		}
		int center = nHalfDots;
		contrib[center] = 1.0;
		double weight = 0.0;
		int i = 0;
		for (i = 1; i <= center; i++) {
			contrib[center + i] = Lanczos(i, width, scaleWidth, support);
			weight += contrib[center + i];
		}
		for (i = center - 1; i >= 0; i--) {
			contrib[i] = contrib[center * 2 - i];
		}
		weight = weight * 2 + 1.0;
		for (i = 0; i <= center; i++) {
			normContrib[i] = contrib[i] / weight;
		}
		for (i = center + 1; i < nDots; i++) {
			normContrib[i] = normContrib[center * 2 - i];
		}
	} // end of CalContrib()
		// 处理边缘

	private void CalTempContrib(int start, int stop) {
		double weight = 0;
		int i = 0;
		for (i = start; i <= stop; i++) {
			weight += contrib[i];
		}
		for (i = start; i <= stop; i++) {
			tmpContrib[i] = contrib[i] / weight;
		}
	} // end of CalTempContrib()

	private int GetRedValue(int rgbValue) {
		int temp = rgbValue & 0x00ff0000;
		return temp >> 16;
	}

	private int GetGreenValue(int rgbValue) {
		int temp = rgbValue & 0x0000ff00;
		return temp >> 8;
	}

	private int GetBlueValue(int rgbValue) {
		return rgbValue & 0x000000ff;
	}

	private int ComRGB(int redValue, int greenValue, int blueValue) {
		return (redValue << 16) + (greenValue << 8) + blueValue;
	}

	// 行水平滤波
	private int HorizontalFilter(BufferedImage bufImg, int startX, int stopX,
			int start, int stop, int y, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;
		for (i = startX, j = start; i <= stopX; i++, j++) {
			valueRGB = bufImg.getRGB(i, y);
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
		}
		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen),
				Clip((int) valueBlue));
		return valueRGB;
	} // end of HorizontalFilter()
		// 图片水平滤波

	private BufferedImage HorizontalFiltering(BufferedImage bufImage, int iOutW) {
		int dwInW = bufImage.getWidth();
		int dwInH = bufImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iOutW, dwInH,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < iOutW; x++) {
			int startX;
			int start;
			int X = (int) (((double) x) * ((double) dwInW) / ((double) iOutW) + 0.5);
			int y = 0;
			startX = X - nHalfDots;
			if (startX < 0) {
				startX = 0;
				start = nHalfDots - X;
			} else {
				start = 0;
			}
			int stop;
			int stopX = X + nHalfDots;
			if (stopX > (dwInW - 1)) {
				stopX = dwInW - 1;
				stop = nHalfDots + (dwInW - 1 - X);
			} else {
				stop = nHalfDots * 2;
			}
			if (start > 0 || stop < nDots - 1) {
				CalTempContrib(start, stop);
				for (y = 0; y < dwInH; y++) {
					value = HorizontalFilter(bufImage, startX, stopX, start,
							stop, y, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (y = 0; y < dwInH; y++) {
					value = HorizontalFilter(bufImage, startX, stopX, start,
							stop, y, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	} // end of HorizontalFiltering()

	private int VerticalFilter(BufferedImage pbInImage, int startY, int stopY,
			int start, int stop, int x, double[] pContrib) {
		double valueRed = 0.0;
		double valueGreen = 0.0;
		double valueBlue = 0.0;
		int valueRGB = 0;
		int i, j;
		for (i = startY, j = start; i <= stopY; i++, j++) {
			valueRGB = pbInImage.getRGB(x, i);
			valueRed += GetRedValue(valueRGB) * pContrib[j];
			valueGreen += GetGreenValue(valueRGB) * pContrib[j];
			valueBlue += GetBlueValue(valueRGB) * pContrib[j];
			// System.out.println(valueRed+"->"+Clip((int)valueRed)+"<-");
			//
			// System.out.println(valueGreen+"->"+Clip((int)valueGreen)+"<-");
			// System.out.println(valueBlue+"->"+Clip((int)valueBlue)+"<-"+"-->");
		}
		valueRGB = ComRGB(Clip((int) valueRed), Clip((int) valueGreen),
				Clip((int) valueBlue));
		// System.out.println(valueRGB);
		return valueRGB;
	} // end of VerticalFilter()

	private BufferedImage VerticalFiltering(BufferedImage pbImage, int iOutH) {
		int iW = pbImage.getWidth();
		int iH = pbImage.getHeight();
		int value = 0;
		BufferedImage pbOut = new BufferedImage(iW, iOutH,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < iOutH; y++) {
			int startY;
			int start;
			int Y = (int) (((double) y) * ((double) iH) / ((double) iOutH) + 0.5);
			startY = Y - nHalfDots;
			if (startY < 0) {
				startY = 0;
				start = nHalfDots - Y;
			} else {
				start = 0;
			}
			int stop;
			int stopY = Y + nHalfDots;
			if (stopY > (int) (iH - 1)) {
				stopY = iH - 1;
				stop = nHalfDots + (iH - 1 - Y);
			} else {
				stop = nHalfDots * 2;
			}
			if (start > 0 || stop < nDots - 1) {
				CalTempContrib(start, stop);
				for (int x = 0; x < iW; x++) {
					value = VerticalFilter(pbImage, startY, stopY, start, stop,
							x, tmpContrib);
					pbOut.setRGB(x, y, value);
				}
			} else {
				for (int x = 0; x < iW; x++) {
					value = VerticalFilter(pbImage, startY, stopY, start, stop,
							x, normContrib);
					pbOut.setRGB(x, y, value);
				}
			}
		}
		return pbOut;
	} // end of VerticalFiltering()

	int Clip(int x) {
		if (x < 0)
			return 0;
		if (x > 255)
			return 255;
		return x;
	}

	/****************************************************************
	 * ScaleImage End
	 * ****************************************************************/
	/****************************************************************
	 * CompressPicDemo Start
	 * ****************************************************************/
	/*
	 * 获得图片大小 传入参数 String path ：图片路径
	 */
	public static long getPicSize(String path) {
		file = new File(path);
		return file.length();
	}

	// 图片处理
	public static String compressPic() {
		try {
			System.out.println(inputDir + inputFileName);
			// 获得源文件
			file = new File(inputDir + inputFileName);
			if (!file.exists()) {
				return "";
			}
			Image img = ImageIO.read(file);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				return "no";
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (proportion == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null))
							/ (double) outputWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) outputHeight + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = outputWidth; // 输出的图片宽度
					newHeight = outputHeight; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outputDir
						+ outputFileName);
				// JPEGImageEncoder可适用于其他图片类型的转换
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "ok";
	}

	/**
	 * 图片大小缩放
	 *
	 * @param img
	 *            原始图片
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @param proportion
	 *            是否等比例缩放
	 * @return Image
	 */
	public static Image compressPic(Image img, int width, int height,
			boolean proportion) {
		BufferedImage image = null;
		// 判断图片格式是否正确
		if (null == img || img.getWidth(null) == -1) {
			return null;
		} else {
			if (width <= 0) {
				width = img.getWidth(null);
			}
			if (height <= 0) {
				height = img.getHeight(null);
			}
			// 判断是否是等比缩放
			if (proportion == true) {
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = img.getWidth(null) / width + 0.1;
				double rate2 = img.getHeight(null) / height + 0.1;
				// 根据缩放比率大的进行缩放控制
				double rate = rate1 > rate2 ? rate1 : rate2;
				width = (int) (img.getWidth(null) / rate);
				height = (int) (img.getHeight(null) / rate);
			}
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			image.getGraphics().drawImage(
					img.getScaledInstance(width, height, Image.SCALE_SMOOTH),
					0, 0, null);
			image.flush();
		}
		return image;
	}

	public String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName) {
		// 输入图路径
		this.inputDir = inputDir;
		// 输出图路径
		this.outputDir = outputDir;
		// 输入图文件名
		this.inputFileName = inputFileName;
		// 输出图文件名
		this.outputFileName = outputFileName;
		return compressPic();
	}

	public static String compressPic(String inputDir, String outputDir,
			String inputFileName, String outputFileName, int width, int height,
			boolean gp) {
		// 输入图路径
		ImageUtils.inputDir = inputDir;
		// 输出图路径
		ImageUtils.outputDir = outputDir;
		// 输入图文件名
		ImageUtils.inputFileName = inputFileName;
		// 输出图文件名
		ImageUtils.outputFileName = outputFileName;
		// 设置图片长宽
		ImageUtils.setWidthAndHeight(width, height);
		// 是否是等比缩放 标记
		ImageUtils.proportion = gp;
		return compressPic();
	}

	public static void setWidthAndHeight(int width, int height) {
		outputWidth = width;
		outputHeight = height;
	}

	/****************************************************************
	 * CompressPicDemo End
	 * ****************************************************************/
	/****************************************************************
	 * EasyImage.jar Begin
	 * ****************************************************************/
	public ImageUtils(File imageFile) {
		try {
			this.bufferedImage = ImageIO.read(imageFile);
			this.fileName = imageFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			this.bufferedImage = null;
			imageFile = null;
		}
	}

	public ImageUtils(String imageFilePath) {
		this(new File(imageFilePath));
	}

	public BufferedImage getAsBufferedImage() {
		return this.bufferedImage;
	}

	public void saveAs(String fileName) {
		saveImage(new File(fileName));
		this.fileName = fileName;
	}

	public void save() {
		saveImage(new File(this.fileName));
	}

	public void resize(int percentOfOriginal) {
		int newWidth = this.bufferedImage.getWidth() * percentOfOriginal / 100;
		int newHeight = this.bufferedImage.getHeight() * percentOfOriginal
				/ 100;
		resize(newWidth, newHeight);
	}

	public void resize(int newWidth, int newHeight) {
		int oldWidth = this.bufferedImage.getWidth();
		int oldHeight = this.bufferedImage.getHeight();

		if ((newWidth == -1) || (newHeight == -1)) {
			if (newWidth == -1) {
				if (newHeight == -1) {
					return;
				}

				newWidth = newHeight * oldWidth / oldHeight;
			} else {
				newHeight = newWidth * oldHeight / oldWidth;
			}
		}

		BufferedImage result = new BufferedImage(newWidth, newHeight, 4);

		int widthSkip = oldWidth / newWidth;
		int heightSkip = oldHeight / newHeight;

		if (widthSkip == 0)
			widthSkip = 1;
		if (heightSkip == 0)
			heightSkip = 1;

		for (int x = 0; x < oldWidth; x += widthSkip) {
			for (int y = 0; y < oldHeight; y += heightSkip) {
				int rgb = this.bufferedImage.getRGB(x, y);

				if ((x / widthSkip < newWidth) && (y / heightSkip < newHeight)) {
					result.setRGB(x / widthSkip, y / heightSkip, rgb);
				}
			}

		}

		this.bufferedImage = result;
	}

	public void addPixelColor(int numToAdd) {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				this.bufferedImage.setRGB(x, y, rgb + numToAdd);
			}
	}

	public void convertToBlackAndWhite() {
		ColorSpace gray_space = ColorSpace.getInstance(1003);
		ColorConvertOp convert_to_gray_op = new ColorConvertOp(gray_space, null);
		convert_to_gray_op.filter(this.bufferedImage, this.bufferedImage);
	}

	public void rotateLeft() {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(height, width, 4);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				result.setRGB(y, x, rgb);
			}
		}

		this.bufferedImage = result;
	}

	public void rotateRight() {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(height, width, 4);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				result.setRGB(height - y - 1, x, rgb);
			}
		}

		this.bufferedImage = result;
	}

	public void rotate180() {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width, height, 4);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				result.setRGB(width - x - 1, height - y - 1, rgb);
			}
		}

		this.bufferedImage = result;
	}

	public void flipHorizontally() {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width, height, 4);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				result.setRGB(width - x - 1, y, rgb);
			}
		}

		this.bufferedImage = result;
	}

	public void flipVertically() {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width, height, 4);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				result.setRGB(x, height - y - 1, rgb);
			}
		}

		this.bufferedImage = result;
	}

	public void multiply(int timesToMultiplyVertically,
			int timesToMultiplyHorizantelly) {
		multiply(timesToMultiplyVertically, timesToMultiplyHorizantelly, 0);
	}

	public void multiply(int timesToMultiplyVertically,
			int timesToMultiplyHorizantelly, int colorToHenhancePerPixel) {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width
				* timesToMultiplyVertically, height
				* timesToMultiplyHorizantelly, 4);

		for (int xx = 0; xx < timesToMultiplyVertically; xx++) {
			for (int yy = 0; yy < timesToMultiplyHorizantelly; yy++) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int rgb = this.bufferedImage.getRGB(x, y);
						result.setRGB(width * xx + x, height * yy + y, rgb
								+ colorToHenhancePerPixel * (yy + xx));
					}
				}
			}

		}

		this.bufferedImage = result;
	}

	public void combineWithPicture(String newImagePath) {
		combineWithPicture(newImagePath, 2);
	}

	public void combineWithPicture(String newImagePath, int jump) {
		try {
			BufferedImage bufferedImage2 = ImageIO.read(new File(newImagePath));
			combineWithPicture(bufferedImage2, jump, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void combineWithPicture(ImageUtils image2) {
		combineWithPicture(image2.getAsBufferedImage(), 2, null);
	}

	public void combineWithPicture(ImageUtils image2, int jump) {
		combineWithPicture(image2.getAsBufferedImage(), jump, null);
	}

	public void combineWithPicture(ImageUtils image2, Color ignoreColor) {
		combineWithPicture(image2.getAsBufferedImage(), 2, ignoreColor);
	}

	public void combineWithPicture(ImageUtils image2, int jump,
			Color ignoreColor) {
		combineWithPicture(image2.getAsBufferedImage(), jump, ignoreColor);
	}

	private void combineWithPicture(BufferedImage bufferedImage2, int jump,
			Color ignoreColor) {
		checkJump(jump);

		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		int width2 = bufferedImage2.getWidth();
		int height2 = bufferedImage2.getHeight();

		int ignoreColorRgb = -1;

		if (ignoreColor != null) {
			ignoreColorRgb = ignoreColor.getRGB();
		}

		for (int y = 0; y < height; y++)
			for (int x = y % jump; x < width; x += jump)
				if ((x < width2) && (y < height2)) {
					int rgb = bufferedImage2.getRGB(x, y);

					if (rgb != ignoreColorRgb)
						this.bufferedImage.setRGB(x, y, rgb);
				}
	}

	public void crop(int startX, int startY, int endX, int endY) {
		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		if (startX == -1) {
			startX = 0;
		}

		if (startY == -1) {
			startY = 0;
		}

		if (endX == -1) {
			endX = width - 1;
		}

		if (endY == -1) {
			endY = height - 1;
		}

		BufferedImage result = new BufferedImage(endX - startX + 1, endY
				- startY + 1, 4);

		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				int rgb = this.bufferedImage.getRGB(x, y);
				result.setRGB(x - startX, y - startY, rgb);
			}
		}
		this.bufferedImage = result;
	}

	private void saveImage(File file) {
		try {
			ImageIO.write(this.bufferedImage, getFileType(file), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void emphasize(int startX, int startY, int endX, int endY) {
		emphasize(startX, startY, endX, endY, Color.BLACK, 3);
	}

	public void emphasize(int startX, int startY, int endX, int endY,
			Color backgroundColor) {
		emphasize(startX, startY, endX, endY, backgroundColor, 3);
	}

	public void emphasize(int startX, int startY, int endX, int endY, int jump) {
		emphasize(startX, startY, endX, endY, Color.BLACK, jump);
	}

	public void emphasize(int startX, int startY, int endX, int endY,
			Color backgroundColor, int jump) {
		checkJump(jump);

		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		if (startX == -1) {
			startX = 0;
		}

		if (startY == -1) {
			startY = 0;
		}

		if (endX == -1) {
			endX = width - 1;
		}

		if (endY == -1) {
			endY = height - 1;
		}

		for (int y = 0; y < height; y++)
			for (int x = y % jump; x < width; x += jump) {
				if ((y < startY) || (y > endY) || (x < startX) || (x > endX)) {
					this.bufferedImage.setRGB(x, y, backgroundColor.getRGB());
				}
			}
	}

	private void checkJump(int jump) {
		if (jump < 1)
			throw new RuntimeException("Error: jump can not be less than 1");
	}

	public void addColorToImage(Color color, int jump) {
		addColorToImage(color.getRGB(), jump);
	}

	public void addColorToImage(int rgb, int jump) {
		checkJump(jump);

		int width = this.bufferedImage.getWidth();
		int height = this.bufferedImage.getHeight();

		for (int y = 0; y < height; y++)
			for (int x = y % jump; x < width; x += jump)
				this.bufferedImage.setRGB(x, y, rgb);
	}

	public void affineTransform(double fShxFactor, double fShyFactor) {
		try {
			AffineTransform shearer = AffineTransform.getShearInstance(
					fShxFactor, fShyFactor);
			AffineTransformOp shear_op = new AffineTransformOp(shearer, null);
			this.bufferedImage = shear_op.filter(this.bufferedImage, null);
		} catch (Exception e) {
			System.out.println("Shearing exception = " + e);
		}
	}

	private String getFileType(File file) {
		String fileName = file.getName();
		int idx = fileName.lastIndexOf(".");
		if (idx == -1) {
			throw new RuntimeException("Invalid file name");
		}

		return fileName.substring(idx + 1);
	}

	public int getWidth() {
		return this.bufferedImage.getWidth();
	}

	public int getHeight() {
		return this.bufferedImage.getHeight();
	}

	/****************************************************************
	 * EasyImage.jar End
	 * ****************************************************************/
}