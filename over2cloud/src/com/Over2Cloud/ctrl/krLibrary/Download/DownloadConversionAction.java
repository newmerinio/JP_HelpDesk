package com.Over2Cloud.ctrl.krLibrary.Download;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.SessionFactory;

import aspose.pdf.Pdf;
import aspose.pdf.Text;

import com.Over2Cloud.CommonClasses.CreateFolderOs;
import com.Over2Cloud.Rnd.CommonConFactory;
import com.Over2Cloud.Rnd.CommonOperInterface;
import com.Over2Cloud.Rnd.TableColumes;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;
import com.aspose.slides.PdfOptions;
import com.aspose.slides.PdfTextCompression;
import com.aspose.slides.Presentation;
import com.aspose.words.BreakType;
import com.aspose.words.ConvertUtil;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.PageSetup;
import com.aspose.words.PdfCompliance;
import com.aspose.words.PdfSaveOptions;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.RelativeVerticalPosition;
import com.aspose.words.WrapType;

public class DownloadConversionAction
{
	  public String documentToPDF(String file)
	  {
		  String fileName=null;
		  try
		  {
			  String fileExtension=FilenameUtils.getExtension(file);
			  String renameFilePath = new CreateFolderOs().createUserDir("krUploadDocs")+"/";
			  if (fileExtension!=null && (fileExtension.equalsIgnoreCase("docx") || fileExtension.equalsIgnoreCase("doc")) )
			  {
				  Document doc = new Document(renameFilePath+""+file);
				  doc.save(renameFilePath+"/"+file+".pdf");
				  fileName=renameFilePath+"/"+file+".pdf";
			  }
			  else if(fileExtension!=null && (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpeg") ))
			  {
				  fileName= convertImageToPdf(renameFilePath+""+file, renameFilePath+""+file+".pdf");
			  }
			  else if(fileExtension!=null && (fileExtension.equalsIgnoreCase("xls")|| fileExtension.equalsIgnoreCase("xlsx")))
			  {
				  if ( fileExtension.equalsIgnoreCase("xlsx")) 
				  {
					  System.out.println(">>>>>>xlsx ");
					  Workbook workbook = new Workbook(renameFilePath+""+file);
					  PdfSaveOptions saveOptions = new PdfSaveOptions();
					  saveOptions.setCompliance(PdfCompliance.PDF_A_1_B);
					  workbook.save(renameFilePath + file+".pdf");
					 fileName=renameFilePath+"/"+file+".pdf";
				  }
				  else
				  {
					  System.out.println(">>>>>>xls ");
					  Workbook workbook = new Workbook(renameFilePath + file);
					  workbook.save(renameFilePath + file+".pdf", SaveFormat.PDF);
					  fileName=renameFilePath+"/"+file+".pdf";
				  }
			  }
			  else if(fileExtension.equalsIgnoreCase("txt"))
			  {
				  System.out.println(">>>>>>txt ");
				  try{
					Pdf pdf1 = new Pdf();        
					aspose.pdf.Section sec1 = pdf1.getSections().add();
					FileInputStream fstream = new FileInputStream(renameFilePath + file);
					    	DataInputStream in = new DataInputStream(fstream);
					        BufferedReader br = new BufferedReader(new InputStreamReader(in));
					    	String strLine;
						 while ((strLine = br.readLine()) != null)   
						{
						 	//Create a new text paragraph and pass the text to its constructor as argument
						  	Text text1 = new Text(sec1,strLine);
						   	sec1.getParagraphs().add(text1);
						}

					    	in.close();
					        pdf1.save(renameFilePath + file+".pdf");
					        fileName=renameFilePath+"/"+file+".pdf";

					    }catch(java.io.IOException ioe){
					       System.out.println(ioe.getMessage());
					      }catch(Exception e){
					       System.out.println(e.getMessage());
					      }
			  }
			  else if(fileExtension.equalsIgnoreCase("pptx"))
			  {
				  System.out.println(">>>>>>pptx ");
				  Presentation pres = new Presentation(renameFilePath+""+file);
				/*  pres.save(renameFilePath+"/"+file+".pdf", SaveFormat.PDF);
				  fileName=renameFilePath+"/"+file+".pdf";*/
				//Instantiate a Presentation object that represents a presentation file

				  //Instantiate the PdfOptions class
				  PdfOptions opts = new PdfOptions();

				  //Set Jpeg Quality
				  opts.setJpegQuality ((byte) 90);

				  //Define behavior for metafiles
				  opts.setSaveMetafilesAsPng ( true);

				  //Set Text Compression level
				  opts.setTextCompression ( PdfTextCompression.Flate);

				  //Define the PDF standard
				  opts.setCompliance(PdfCompliance.PDF_15);

				  //Save the presentation to PDF with specified options
				  pres.save(renameFilePath+"/"+file+".pdf", SaveFormat.PDF,opts);
				  fileName=renameFilePath+"/"+file+".pdf";
			  }
			  else
			  {
				  fileName=renameFilePath+"/"+file;
			  }
		 }
		 catch (Exception e)
		 {
			e.printStackTrace();
		 }
		  return fileName;
	  }
	  public static String convertImageToPdf(String inputFileName, String outputFileName) throws Exception
	    {
	        // Create Aspose.Words.Document and DocumentBuilder.
	        // The builder makes it simple to add content to the document.
		    String fileName=null;
	        Document doc = new Document();
	        DocumentBuilder builder = new DocumentBuilder(doc);

	        // Load images from the disk using the approriate reader.
	        // The file formats that can be loaded depends on the image readers available on the machine.
	        ImageInputStream iis = ImageIO.createImageInputStream(new File(inputFileName));
	        ImageReader reader = ImageIO.getImageReaders(iis).next();
	        reader.setInput(iis, false);

	        try
	        {
	            // Get the number of frames in the image.
	            int framesCount = reader.getNumImages(true);

	            // Loop through all frames.
	            for (int frameIdx = 0; frameIdx < framesCount; frameIdx++)
	            {
	                // Insert a section break before each new page, in case of a multi-frame image.
	                if (frameIdx != 0)
	                    builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);

	                // Select active frame.
	                BufferedImage image = reader.read(frameIdx);

	                // We want the size of the page to be the same as the size of the image.
	                // Convert pixels to points to size the page to the actual image size.
	                PageSetup ps = builder.getPageSetup();

	                ps.setPageWidth(ConvertUtil.pixelToPoint(image.getWidth()));
	                ps.setPageHeight(ConvertUtil.pixelToPoint(image.getHeight()));

	                // Insert the image into the document and position it at the top left corner of the page.
	                builder.insertImage(
	                    image,
	                    RelativeHorizontalPosition.PAGE,
	                    0,
	                    RelativeVerticalPosition.PAGE,
	                    0,
	                    ps.getPageWidth(),
	                    ps.getPageHeight(),
	                    WrapType.NONE);
	            }
	        }

	        finally {
	            if (iis != null) {
	                iis.close();
	                reader.dispose();
	            }
	        }

	        // Save the document to PDF.
	        doc.save(outputFileName);
	        fileName=outputFileName;
	        return fileName;
	    }
	public boolean createTableFields(SessionFactory connectionSpace) 
	{	
		boolean status=false;
		 try 
		 {
				CommonOperInterface cbt = new CommonConFactory().createInterface();
				// Code for creating table dynamically
				List<TableColumes> Tablecolumesaaa = new ArrayList<TableColumes>();
				// add some extra fields
				TableColumes ob2 = new TableColumes();
				// employee id
				ob2 = new TableColumes();
				ob2.setColumnname("share_id");
				ob2.setDatatype("varchar(255)");
				ob2.setConstraint("default NULL");
				Tablecolumesaaa.add(ob2);
			
				ob2 = new TableColumes();
				ob2.setColumnname("user_name");
				ob2.setDatatype("varchar(50)");
				ob2.setConstraint("default NULL");
				Tablecolumesaaa.add(ob2);
			
				// openDate
				ob2 = new TableColumes();
				ob2.setColumnname("created_date");
				ob2.setDatatype("varchar(100)");
				ob2.setConstraint("default NULL");
				Tablecolumesaaa.add(ob2);
				// openDate
				ob2 = new TableColumes();
				ob2.setColumnname("created_time");
				ob2.setDatatype("varchar(50)");
				ob2.setConstraint("default NULL");
				Tablecolumesaaa.add(ob2);
			
				status = cbt.createTable22("kr_share_download_history", Tablecolumesaaa, connectionSpace);
		    }
			catch (Exception e) 
		    {
				e.printStackTrace();
		    }
		return status;
	}
	  
}
