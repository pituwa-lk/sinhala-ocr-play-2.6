package ocr.utils

import java.awt.image._
import java.io.{File, IOException}
import javax.imageio.ImageIO

/**
  * Created by nayana on 8/19/17.
  */
object ImageUtils {

  val threshold = 110

  def flattenImageBlack(imgFile: String): Unit = {
    var img:BufferedImage = null
    try
      img = ImageIO.read(new File(imgFile))
    catch {
      case e: IOException =>
    }

    val height = img.getHeight
    val width = img.getWidth
    val fBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)
    val upperLimit: Int = width * height
    val buffer = img.getRaster.getDataBuffer
    val source = buffer.asInstanceOf[DataBufferByte].getData

    val destination = new Array[Byte](upperLimit)

    var i = 0
    while ( {
      i < upperLimit
    }) {
      val red = (source(i) >> 16) & 0x000000FF
      if (red > threshold) destination(i) = -1

      {
        i += 1; i - 1
      }
    }

    val raster = Raster.createPackedRaster(DataBuffer.TYPE_BYTE, width, height, 1, 2, null)
    raster.setDataElements(0, 0, width, height, destination)
    fBufferedImage.setData(raster)
    val bImageGraphics = fBufferedImage.createGraphics
    bImageGraphics.drawImage(fBufferedImage, null, null)

    val rImage = fBufferedImage.asInstanceOf[RenderedImage]
    try {
      val t = new File("c2po.BMP")
      ImageIO.write(rImage, "BMP", t)
    } catch {
      case e:Exception =>
    }
  }

  def flattenImage(imgFile: String) = {

    var img: BufferedImage = null
    try
      img = ImageIO.read(new File(imgFile))
    catch {
      case e: IOException =>
    }

    val height = img.getHeight
    val width = img.getWidth
    val bw = Array(0xff.toByte, 0.toByte)
    val blackAndWhite = new IndexColorModel(1, 2, bw, bw, bw)
    val fBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED, blackAndWhite)
    val upperLimit: Int = width * height
    val buffer = img.getRaster.getDataBuffer
    val source = buffer.asInstanceOf[DataBufferByte].getData

    val destination = new Array[Byte](upperLimit)

    var i = 0
    while ( {
      i < upperLimit
    }) {
      val red = (source(i) >> 16) & 0x000000FF
      if (red > threshold) destination(i) = -1

      {
        i += 1
        i - 1
      }
    }

    val raster = Raster.createPackedRaster(DataBuffer.TYPE_BYTE, width, height, 1, 2, null)
    raster.setDataElements(0, 0, width, height, destination)
    fBufferedImage.setData(raster)
    val bImageGraphics = fBufferedImage.createGraphics
    bImageGraphics.drawImage(fBufferedImage, null, null)
    val rImage = fBufferedImage.asInstanceOf[RenderedImage]
    try {
      val t = new File("test-r2d2.JPG")
      ImageIO.write(rImage, "JPG", t)
    } catch {
      case e: Exception =>
    }
  }
}
