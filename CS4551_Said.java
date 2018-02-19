import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

	public class CS4551_Said{

		
		/*
		 *   |                                             |
		 *   |---------------------------------------------|
		 *   |											  |
		 *   |      Multimedia HW - 1                      |
		 * 	 |                                             |
		 *   |      Author: Naif Said                      |
		 *   |      Date:  Feb, 18 2018                    |
		 *   |                                             |
		 *   |-------------------------------------------- |
		 *   |                                             |
		 */
		
		
		
		
	private static int option;
	private static int i; 
	private static int[] indexlevel8 = {32,64,96,128,160,192,224,256}; // threshold value for 8 level
	private static int[] indexlevel16 = {16,32,48,64,80,96,112,128,144,160,176,192,208,224,240,256};
	private static int[] index4level = {64,128,192};   // threshold values for 4 level. 
	private static int[] bytevaluesforredandgreen = {000,001,010,011,100,101,110,111};
	private static int[] bytevalueforblue = {00,01,10,11}; 
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
    
        do{

            System.out.println("Main Menu-----------------------------------\n"+
                               "1. Conversion to Gray-scale Image (24bits->8bits)\n"+
                               "2. Conversion to N-level Image\n"+
                               "3. Conversion to 8bit Indexed Color Image using Uniform Color Quantization (24bits->8bits)\n"+
                                "4. Quit\n"+
                                "Please enter the task number [1-5]:\n");
                        option = sc.nextInt();         
                switch(option){
                    case 1: task1(args[0]);
                     break;
                    case 2: task2(args[0]); 
                    	break;
                    case 3: uniformColorQuantization(args[0]); 
                    break;
                    case 4: System.out.println("Application Terminated..."); break;
                    default:
                    System.out.println("Sorry, Not a number in the options...");
                }

        }while(option != 4);
        System.exit(0);
        sc.close();
    }
    
    public static Image task1(String imgN) {
    		
    		Image imgObj = new Image(imgN);
    		convertToGray(imgObj);
    		imgObj.write2PPM("grayducky.ppm");
    		imgObj.display();
    		return imgObj;
    }
  
     
    public static void task2(String imgN){
    			Scanner sc = new Scanner(System.in);
			System.out.print("Choose the value of N-level(2,4,8,16): ");
			int n = sc.nextInt();
			
			// choose the N-level
		switch(n) {
		case 2: 
			errordiffalllevels(imgN, 2);	    	
			break;
			
		case 4: 
			errordiffalllevels(imgN, 4);
		break;
		case 8:
			errordiffalllevels(imgN, 8);
		break;
		case 16:
			errordiffalllevels(imgN, 16);
		break;
			default:
			System.out.println("Sorry, Not a number in the options...");
		}
    }
    
    // convert image to gray and return image object
    public static Image convertToGray(Image imageObject) {
	
		int[] rgb = new int[3];
		for(int y=0;y<imageObject.getH();y++){
			for(int x=0;x<imageObject.getW();x++){
				imageObject.getPixel(x, y, rgb);
				int gray = (int) Math.round((0.299 * (rgb[0])) + 0.587 * (rgb[1]) + 0.114 * (rgb[2]));
				rgb[0] =  (0xFF & gray);
				rgb[1] =  (0xFF & gray);
				rgb[2] =  (0xFF & gray);
				imageObject.setPixel(x, y, rgb);
			}
		}
	return imageObject;
}
    
    public static Image convertTo2level(Image imgObj) {
		
		int[] rgb = new int[3];
		for(int y=0;y<imgObj.getH();y++){
			for(int x=0;x<imgObj.getW();x++){
				imgObj.getPixel(x, y, rgb);
				
				// use the threshold to determine the color. 
				if(rgb[0] >= 127) {
					rgb[0] = 255;
					rgb[1] = 255;
					rgb[2] = 255;
					}else {
					rgb[0] = 0;
					rgb[1] = 0;
					rgb[2] = 0;
					}
				imgObj.setPixel(x, y, rgb);
			}
		}
		
		return imgObj;
    }
    
    
public static Image convertTo4level(Image imgObj) {
    	
		
		 int[] rgb = new int[3];
		for(int y=0;y<imgObj.getH();y++){
			for(int x=0;x<imgObj.getW();x++){
				imgObj.getPixel(x, y, rgb);
				
				// use the threshold to determine the color. 
				if(rgb[0] <= index4level[0]) {
						rgb[0] = 0*64+32;
						rgb[1] = 0*64+32;
						rgb[2] = 0*64+32;
					}else if(rgb[0] <= index4level[1] && rgb[0] > index4level[0]) {
						rgb[0] = 1*64+32;
						rgb[1] = 1*64+32;
						rgb[2] = 1*64+32;
					} else if (rgb[0] <= index4level[2] && rgb[0] > index4level[1]) {
						rgb[0] = 2*64+32;
						rgb[1] = 2*64+32;
						rgb[2] = 2*64+32;
					}else {
						rgb[0] = 3*64+32;
						rgb[1] = 3*64+32;
						rgb[2] = 3*64+32;
					}
				imgObj.setPixel(x, y, rgb);
			}
		}
		
		return imgObj;
    }

	public static Image convertTo8level(Image imgObj) {
		
		
		 int[] rgb = new int[3];
		for(int y=0;y<imgObj.getH();y++){
			for(int x=0;x<imgObj.getW();x++){
				imgObj.getPixel(x, y, rgb);
				
				
				//use the threshold to determine the color. 
				if(rgb[0] <= indexlevel8[0]) {
						rgb[0] = 0*32+16;
						rgb[1] = 0*32+16;
						rgb[2] = 0*32+16;
					}else if(rgb[0] <= indexlevel8[1] && rgb[0] > indexlevel8[0]) {
						rgb[0] = 1*32+16;
						rgb[1] = 1*32+16;
						rgb[2] = 1*32+16;
					} else if (rgb[0] <= indexlevel8[2] && rgb[0] > indexlevel8[1]) {
						rgb[0] = 2*32+16;
						rgb[1] = 2*32+16;
						rgb[2] = 2*32+16;
					}else if (rgb[0] <= indexlevel8[3] && rgb[0] > indexlevel8[2]) {
						rgb[0] = 3*32+16;
						rgb[1] = 3*32+16;
						rgb[2] = 3*32+16;
					}else if (rgb[0] <= indexlevel8[4] && rgb[0] > indexlevel8[3]) {
						rgb[0] = 4*32+16;
						rgb[1] = 4*32+16;
						rgb[2] = 4*32+16;
					}else if (rgb[0] <= indexlevel8[5] && rgb[0] > indexlevel8[4]) {
						rgb[0] = 5*32+16;
						rgb[1] = 5*32+16;
						rgb[2] = 5*32+16;
					}else if (rgb[0] <= indexlevel8[6] && rgb[0] > indexlevel8[5]) {
						rgb[0] = 6*32+16;
						rgb[1] = 6*32+16;
						rgb[2] = 6*32+16;
					}else if (rgb[0] <= indexlevel8[7] && rgb[0] > indexlevel8[6]) {
						rgb[0] = 7*32+16;
						rgb[1] = 7*32+16;
						rgb[2] = 7*32+16;
					}
				
				imgObj.setPixel(x, y, rgb);
			}
		}
		
		return imgObj;
	}
	
	
	public static Image convertTo16level(Image imgObj) {
		
		
		 int[] rgb = new int[3];
		for(int y=0;y<imgObj.getH();y++){
			for(int x=0;x<imgObj.getW();x++){
				imgObj.getPixel(x, y, rgb);
				
				
				//use the threshold to determine the color. 
				if(rgb[0] <= indexlevel16[0]) {
						rgb[0] = 0*16+8;
						rgb[1] = 0*16+8;
						rgb[2] = 0*16+8;
					}else if(rgb[0] <= indexlevel16[1] && rgb[0] > indexlevel16[0]) {
						rgb[0] = 1*16+8;
						rgb[1] = 1*16+8;
						rgb[2] = 1*16+8;
					} else if (rgb[0] <= indexlevel16[2] && rgb[0] > indexlevel16[1]) {
						rgb[0] = 2*16+8;
						rgb[1] = 2*16+8;
						rgb[2] = 2*16+8;
					}else if (rgb[0] <= indexlevel16[3] && rgb[0] > indexlevel16[2]) {
						rgb[0] = 3*16+8;
						rgb[1] = 3*16+8;
						rgb[2] = 3*16+8;
					}else if (rgb[0] <= indexlevel16[4] && rgb[0] > indexlevel16[3]) {
						rgb[0] = 4*16+8;
						rgb[1] = 4*16+8;
						rgb[2] = 4*16+8;
					}else if (rgb[0] <= indexlevel16[5] && rgb[0] > indexlevel16[4]) {
						rgb[0] = 5*16+8;
						rgb[1] = 5*16+8;
						rgb[2] = 5*16+8;
					}else if (rgb[0] <= indexlevel16[6] && rgb[0] > indexlevel16[5]) {
						rgb[0] = 6*16+8;
						rgb[1] = 6*16+8;
						rgb[2] = 6*16+8;
					}else if (rgb[0] <= indexlevel16[7] && rgb[0] > indexlevel16[6]) {
						rgb[0] = 7*16+8;
						rgb[1] = 7*16+8;
						rgb[2] = 7*16+8;
					}else if (rgb[0] <= indexlevel16[8] && rgb[0] > indexlevel16[7]) {
						rgb[0] = 8*16+8;
						rgb[1] = 8*16+8;
						rgb[2] = 8*16+8;
					}else if (rgb[0] <= indexlevel16[9] && rgb[0] > indexlevel16[8]) {
						rgb[0] = 9*16+8;
						rgb[1] = 9*16+8;
						rgb[2] = 9*16+8;
					}else if (rgb[0] <= indexlevel16[10] && rgb[0] > indexlevel16[9]) {
						rgb[0] = 10*16+8;
						rgb[1] = 10*16+8;
						rgb[2] = 10*16+8;
					}else if (rgb[0] <= indexlevel16[11] && rgb[0] > indexlevel16[10]) {
						rgb[0] = 11*16+8;
						rgb[1] = 11*16+8;
						rgb[2] = 11*16+8;
					}else if (rgb[0] <= indexlevel16[12] && rgb[0] > indexlevel16[11]) {
						rgb[0] = 12*16+8;
						rgb[1] = 12*16+8;
						rgb[2] = 12*16+8;
					}else if (rgb[0] <= indexlevel16[13] && rgb[0] > indexlevel16[12]) {
						rgb[0] = 13*16+8;
						rgb[1] = 13*16+8;
						rgb[2] = 13*16+8;
					}else if (rgb[0] <= indexlevel16[14] && rgb[0] > indexlevel16[13]) {
						rgb[0] = 14*16+8;
						rgb[1] = 14*16+8;
						rgb[2] = 14*16+8;
					}else if (rgb[0] <= indexlevel16[15] && rgb[0] > indexlevel16[14]) {
						rgb[0] = 15*16+8;
						rgb[1] = 15*16+8;
						rgb[2] = 15*16+8;
					}
				
				imgObj.setPixel(x, y, rgb);
			}
		}
		
		return imgObj;
	}


	public static void errordiffalllevels(String imgN, int level) {
		
		// ############################################# error diffusion 
			
			Image imgObj = new Image(imgN);
			
			//1 - convert the image to gray scale.
			convertToGray(imgObj);
			// 	convert the image to N level scale
			
			if(level == 2) {
				convertTo2level(imgObj);
			}else if(level == 4) {
				convertTo4level(imgObj);	
			}else if(level == 8) {
				convertTo8level(imgObj);
			}else if(level == 16) {
				convertTo16level(imgObj);
			}
			
			
			
			imgObj.write2PPM("threshold"+level+".ppm");
			imgObj.display();
		
		
		// new image to apply the error diffusion. 
		Image grayscaleimage = new Image(imgN);
		convertToGray(grayscaleimage);
		int[] grayscalergb = new int[3];
		
		Image blackandwhite = new Image(grayscaleimage.getW(),grayscaleimage.getH());
		int[] rgb = new int[3];
		
		int e = 0;
		for(int y=0;y<blackandwhite.getH();y++){
			for(int x=0;x<blackandwhite.getW();x++){
			
				// set up the original matrix to calculate the error diffusion
				grayscaleimage.getPixel(x, y, grayscalergb);
				
				// bi - level image 
				blackandwhite.getPixel(x, y, rgb);
				
				// calculate the error diffusion. since all channels would have same values so calculate ones
				 e = Math.subtractExact(grayscalergb[0], rgb[0]); 
				 
				 /* 	- calculate the error which is the difference between the gray scale and the 2 level image. 
				 	- then calculate the 4 next pixels by adding the error to them. 
				 	- Use the threshold to convert it to black or white
				 */
				
				 
				     // 	Update four neighbors.
				 	// ### 1
				 	if(x+1 < grayscaleimage.getW()) {
				 		grayscaleimage.getPixel(x+1, y, grayscalergb);
				 		int errordiff = Math.addExact(grayscalergb[0], ((Math.multiplyExact(e, 7)/16)));

				 		grayscalergb[0] = errordiff;
				 		 
				 		if(level == 2) {
				 						
				 						// if level is 2 then 0 or 255
								 		if(errordiff >= 127) {
								 			rgb[0] = 255;
								 			rgb[1] = 255;
								 			rgb[2] = 255;
								 		}else {
								 			rgb[0] = 0;
								 			rgb[1] = 0;
								 			rgb[2] = 0;
								 		}
				 		}else if(level == 4){
							 			
				 			           // if level is 4 then 64,128, 192. 255
				 						int[] index = {64,128,192};
							 			if(errordiff <= index[0]) {
											rgb[0] = 64;
											rgb[1] = 64;
											rgb[2] = 64;
										}else if(errordiff <= index[1] && errordiff > index[0]) {
											rgb[0] = 128;
											rgb[1] = 128;
											rgb[2] = 128;
										} else if (errordiff <= index[2] && errordiff > index[1]) {
											rgb[0] = 192;
											rgb[1] = 192;
											rgb[2] = 192;
										}else {
											rgb[0] = 255;
											rgb[1] = 255;
											rgb[2] = 255;
										}
				 		}else if(level == 8){
				 			
				 			if(errordiff <= indexlevel8[0]) {
								rgb[0] = 32;
								rgb[1] = 32;
								rgb[2] = 32;
							}else if(errordiff <= indexlevel8[1] && errordiff > indexlevel8[0]) {
								rgb[0] = 64;
								rgb[1] = 64;
								rgb[2] = 64;
							} else if (errordiff<= indexlevel8[2] && errordiff > indexlevel8[1]) {
								rgb[0] = 96;
								rgb[1] = 96;
								rgb[2] = 96;
							}else if (errordiff <= indexlevel8[3] && errordiff > indexlevel8[2]) {
								rgb[0] = 128;
								rgb[1] = 128;
								rgb[2] = 128;
							}else if (errordiff <= indexlevel8[4] && errordiff > indexlevel8[3]) {
								rgb[0] = 160;
								rgb[1] = 160;
								rgb[2] = 160;
							}else if (errordiff <= indexlevel8[5] && errordiff > indexlevel8[4]) {
								rgb[0] = 192;
								rgb[1] = 192;
								rgb[2] = 192;
							}else if (errordiff <= indexlevel8[6] && errordiff > indexlevel8[5]) {
								rgb[0] = 224;
								rgb[1] = 224;
								rgb[2] = 224;
							}else if (errordiff <= indexlevel8[7] && errordiff > indexlevel8[6]) {
								rgb[0] = 255;
								rgb[1] = 255;
								rgb[2] = 255;
							}
				 		}else if(level == 16){
				 		
				 			if(errordiff <= indexlevel16[0]) {
							rgb[0] = 16;
							rgb[1] = 16;
							rgb[2] = 16;
						}else if(errordiff <= indexlevel16[1] && errordiff > indexlevel16[0]) {
							rgb[0] = 32;
							rgb[1] = 32;
							rgb[2] = 32;
						} else if (errordiff <= indexlevel16[2] && errordiff > indexlevel16[1]) {
							rgb[0] = 48;
							rgb[1] = 48;
							rgb[2] = 48;
						}else if (errordiff <= indexlevel16[3] && errordiff > indexlevel16[2]) {
							rgb[0] = 64;
							rgb[1] = 64;
							rgb[2] = 64;;
						}else if (errordiff <= indexlevel16[4] && errordiff > indexlevel16[3]) {
							rgb[0] = 80;
							rgb[1] = 80;
							rgb[2] = 80;
						}else if (errordiff <= indexlevel16[5] && errordiff > indexlevel16[4]) {
							rgb[0] = 96;
							rgb[1] = 96;
							rgb[2] = 96;
						}else if (errordiff <= indexlevel16[6] && errordiff > indexlevel16[5]) {
							rgb[0] = 112;
							rgb[1] = 112;
							rgb[2] = 112;;
						}else if (errordiff <= indexlevel16[7] && errordiff > indexlevel16[6]) {
							rgb[0] = 128;
							rgb[1] = 128;
							rgb[2] = 128;
						}else if (errordiff <= indexlevel16[8] && errordiff > indexlevel16[7]) {
							rgb[0] = 144;
							rgb[1] = 144;
							rgb[2] = 144;
						}else if (errordiff <= indexlevel16[9] && errordiff > indexlevel16[8]) {
							rgb[0] = 160;
							rgb[1] = 160;
							rgb[2] = 160;
						}else if (errordiff <= indexlevel16[10] && errordiff > indexlevel16[9]) {
							rgb[0] = 176;
							rgb[1] = 176;
							rgb[2] = 176;
						}else if (errordiff<= indexlevel16[11] && errordiff > indexlevel16[10]) {
							rgb[0] = 192;
							rgb[1] = 192;
							rgb[2] = 192;
						}else if (errordiff <= indexlevel16[12] && errordiff > indexlevel16[11]) {
							rgb[0] = 208;
							rgb[1] = 208;
							rgb[2] = 208;
						}else if (errordiff <= indexlevel16[13] && errordiff > indexlevel16[12]) {
							rgb[0] = 224;
							rgb[1] = 224;
							rgb[2] = 224;
						}else if (errordiff <= indexlevel16[14] && errordiff > indexlevel16[13]) {
							rgb[0] = 240;
							rgb[1] = 240;
							rgb[2] = 240;
						}else if (errordiff <= indexlevel16[15] && errordiff > indexlevel16[14]) {
							rgb[0] = 255;
							rgb[1] = 255;
							rgb[2] = 255;
						}
				 	}
				 		blackandwhite.setPixel(x+1, y, rgb);
				 		grayscaleimage.setPixel(x+1, y, grayscalergb);
				 		
				 	}

					 // ### 2		
					 if(x-1 >= 0 && y+1 < grayscaleimage.getH()) {
						 	grayscaleimage.getPixel(x-1, y+1, grayscalergb);
					 		int errordiff = Math.addExact(grayscalergb[0], ((Math.multiplyExact(e, 3)/16)));
					 		
					 		grayscalergb[0] = errordiff;
					 		
					 		if(level == 2) {
		 						
			 						// if level is 2 then 0 or 255
							 		if(errordiff >= 127) {
							 			rgb[0] = 255;
							 			rgb[1] = 255;
							 			rgb[2] = 255;
							 		}else {
							 			rgb[0] = 0;
							 			rgb[1] = 0;
							 			rgb[2] = 0;
							 		}
					 		}else if(level == 4){
					 			
			 			           // if level is 4 then 64,128, 192. 255
			 						int[] index = {64,128,192};
						 			if(errordiff <= index[0]) {
										rgb[0] = 64;
										rgb[1] = 64;
										rgb[2] = 64;
									}else if(errordiff <= index[1] && errordiff > index[0]) {
										rgb[0] = 128;
										rgb[1] = 128;
										rgb[2] = 128;
									} else if (errordiff <= index[2] && errordiff > index[1]) {
										rgb[0] = 192;
										rgb[1] = 192;
										rgb[2] = 192;
									}else {
										rgb[0] = 255;
										rgb[1] = 255;
										rgb[2] = 255;
									}
		 			}else if(level == 8){
			 			
				 				if(errordiff <= indexlevel8[0]) {
									rgb[0] = 32;
									rgb[1] = 32;
									rgb[2] = 32;
								}else if(errordiff <= indexlevel8[1] && errordiff > indexlevel8[0]) {
									rgb[0] = 64;
									rgb[1] = 64;
									rgb[2] = 64;
								} else if (errordiff<= indexlevel8[2] && errordiff > indexlevel8[1]) {
									rgb[0] = 96;
									rgb[1] = 96;
									rgb[2] = 96;
								}else if (errordiff <= indexlevel8[3] && errordiff > indexlevel8[2]) {
									rgb[0] = 128;
									rgb[1] = 128;
									rgb[2] = 128;
								}else if (errordiff <= indexlevel8[4] && errordiff > indexlevel8[3]) {
									rgb[0] = 160;
									rgb[1] = 160;
									rgb[2] = 160;
								}else if (errordiff <= indexlevel8[5] && errordiff > indexlevel8[4]) {
									rgb[0] = 192;
									rgb[1] = 192;
									rgb[2] = 192;
								}else if (errordiff <= indexlevel8[6] && errordiff > indexlevel8[5]) {
									rgb[0] = 224;
									rgb[1] = 224;
									rgb[2] = 224;
								}else if (errordiff <= indexlevel8[7] && errordiff > indexlevel8[6]) {
									rgb[0] = 255;
									rgb[1] = 255;
									rgb[2] = 255;
								}
			 		}else if(level == 16){
				 		
			 			if(errordiff <= indexlevel16[0]) {
							rgb[0] = 16;
							rgb[1] = 16;
							rgb[2] = 16;
						}else if(errordiff <= indexlevel16[1] && errordiff > indexlevel16[0]) {
							rgb[0] = 32;
							rgb[1] = 32;
							rgb[2] = 32;
						} else if (errordiff <= indexlevel16[2] && errordiff > indexlevel16[1]) {
							rgb[0] = 48;
							rgb[1] = 48;
							rgb[2] = 48;
						}else if (errordiff <= indexlevel16[3] && errordiff > indexlevel16[2]) {
							rgb[0] = 64;
							rgb[1] = 64;
							rgb[2] = 64;;
						}else if (errordiff <= indexlevel16[4] && errordiff > indexlevel16[3]) {
							rgb[0] = 80;
							rgb[1] = 80;
							rgb[2] = 80;
						}else if (errordiff <= indexlevel16[5] && errordiff > indexlevel16[4]) {
							rgb[0] = 96;
							rgb[1] = 96;
							rgb[2] = 96;
						}else if (errordiff <= indexlevel16[6] && errordiff > indexlevel16[5]) {
							rgb[0] = 112;
							rgb[1] = 112;
							rgb[2] = 112;;
						}else if (errordiff <= indexlevel16[7] && errordiff > indexlevel16[6]) {
							rgb[0] = 128;
							rgb[1] = 128;
							rgb[2] = 128;
						}else if (errordiff <= indexlevel16[8] && errordiff > indexlevel16[7]) {
							rgb[0] = 144;
							rgb[1] = 144;
							rgb[2] = 144;
						}else if (errordiff <= indexlevel16[9] && errordiff > indexlevel16[8]) {
							rgb[0] = 160;
							rgb[1] = 160;
							rgb[2] = 160;
						}else if (errordiff <= indexlevel16[10] && errordiff > indexlevel16[9]) {
							rgb[0] = 176;
							rgb[1] = 176;
							rgb[2] = 176;
						}else if (errordiff<= indexlevel16[11] && errordiff > indexlevel16[10]) {
							rgb[0] = 192;
							rgb[1] = 192;
							rgb[2] = 192;
						}else if (errordiff <= indexlevel16[12] && errordiff > indexlevel16[11]) {
							rgb[0] = 208;
							rgb[1] = 208;
							rgb[2] = 208;
						}else if (errordiff <= indexlevel16[13] && errordiff > indexlevel16[12]) {
							rgb[0] = 224;
							rgb[1] = 224;
							rgb[2] = 224;
						}else if (errordiff <= indexlevel16[14] && errordiff > indexlevel16[13]) {
							rgb[0] = 240;
							rgb[1] = 240;
							rgb[2] = 240;
						}else if (errordiff <= indexlevel16[15] && errordiff > indexlevel16[14]) {
							rgb[0] = 255;
							rgb[1] = 255;
							rgb[2] = 255;
						}
			 	}
					 		
					 		blackandwhite.setPixel(x-1, y+1, rgb);
					 		grayscaleimage.setPixel(x-1, y+1, grayscalergb);
					 }
					 
					 // ### 3
					 if(y+1 < grayscaleimage.getH()) {
						 	grayscaleimage.getPixel(x, y+1, grayscalergb);
						 	int errordiff = Math.addExact(grayscalergb[0], ((Math.multiplyExact(e, 5)/16)));
						 	
						 	grayscalergb[0] = errordiff;
						 	if(level == 2) {
		 						
		 						// if level is 2 then 0 or 255
						 		if(errordiff >= 127) {
						 			rgb[0] = 255;
						 			rgb[1] = 255;
						 			rgb[2] = 255;
						 		}else {
						 			rgb[0] = 0;
						 			rgb[1] = 0;
						 			rgb[2] = 0;
						 		}
		 		}else if(level == 4){
					 			
		 			           // if level is 4 then 64,128, 192. 255
		 						int[] index = {64,128,192};
					 			if(errordiff <= index[0]) {
									rgb[0] = 64;
									rgb[1] = 64;
									rgb[2] = 64;
								}else if(errordiff <= index[1] && errordiff > index[0]) {
									rgb[0] = 128;
									rgb[1] = 128;
									rgb[2] = 128;
								} else if (errordiff <= index[2] && errordiff > index[1]) {
									rgb[0] = 192;
									rgb[1] = 192;
									rgb[2] = 192;
								}else {
									rgb[0] = 255;
									rgb[1] = 255;
									rgb[2] = 255;
								}
		 		}else if(level == 8){
		 			
					 			if(errordiff <= indexlevel8[0]) {
									rgb[0] = 32;
									rgb[1] = 32;
									rgb[2] = 32;
								}else if(errordiff <= indexlevel8[1] && errordiff > indexlevel8[0]) {
									rgb[0] = 64;
									rgb[1] = 64;
									rgb[2] = 64;
								} else if (errordiff<= indexlevel8[2] && errordiff > indexlevel8[1]) {
									rgb[0] = 96;
									rgb[1] = 96;
									rgb[2] = 96;
								}else if (errordiff <= indexlevel8[3] && errordiff > indexlevel8[2]) {
									rgb[0] = 128;
									rgb[1] = 128;
									rgb[2] = 128;
								}else if (errordiff <= indexlevel8[4] && errordiff > indexlevel8[3]) {
									rgb[0] = 160;
									rgb[1] = 160;
									rgb[2] = 160;
								}else if (errordiff <= indexlevel8[5] && errordiff > indexlevel8[4]) {
									rgb[0] = 192;
									rgb[1] = 192;
									rgb[2] = 192;
								}else if (errordiff <= indexlevel8[6] && errordiff > indexlevel8[5]) {
									rgb[0] = 224;
									rgb[1] = 224;
									rgb[2] = 224;
								}else if (errordiff <= indexlevel8[7] && errordiff > indexlevel8[6]) {
									rgb[0] = 255;
									rgb[1] = 255;
									rgb[2] = 255;
								}
		 		}	else if(level == 16){
			 		
		 			if(errordiff <= indexlevel16[0]) {
						rgb[0] = 16;
						rgb[1] = 16;
						rgb[2] = 16;
					}else if(errordiff <= indexlevel16[1] && errordiff > indexlevel16[0]) {
						rgb[0] = 32;
						rgb[1] = 32;
						rgb[2] = 32;
					} else if (errordiff <= indexlevel16[2] && errordiff > indexlevel16[1]) {
						rgb[0] = 48;
						rgb[1] = 48;
						rgb[2] = 48;
					}else if (errordiff <= indexlevel16[3] && errordiff > indexlevel16[2]) {
						rgb[0] = 64;
						rgb[1] = 64;
						rgb[2] = 64;;
					}else if (errordiff <= indexlevel16[4] && errordiff > indexlevel16[3]) {
						rgb[0] = 80;
						rgb[1] = 80;
						rgb[2] = 80;
					}else if (errordiff <= indexlevel16[5] && errordiff > indexlevel16[4]) {
						rgb[0] = 96;
						rgb[1] = 96;
						rgb[2] = 96;
					}else if (errordiff <= indexlevel16[6] && errordiff > indexlevel16[5]) {
						rgb[0] = 112;
						rgb[1] = 112;
						rgb[2] = 112;;
					}else if (errordiff <= indexlevel16[7] && errordiff > indexlevel16[6]) {
						rgb[0] = 128;
						rgb[1] = 128;
						rgb[2] = 128;
					}else if (errordiff <= indexlevel16[8] && errordiff > indexlevel16[7]) {
						rgb[0] = 144;
						rgb[1] = 144;
						rgb[2] = 144;
					}else if (errordiff <= indexlevel16[9] && errordiff > indexlevel16[8]) {
						rgb[0] = 160;
						rgb[1] = 160;
						rgb[2] = 160;
					}else if (errordiff <= indexlevel16[10] && errordiff > indexlevel16[9]) {
						rgb[0] = 176;
						rgb[1] = 176;
						rgb[2] = 176;
					}else if (errordiff<= indexlevel16[11] && errordiff > indexlevel16[10]) {
						rgb[0] = 192;
						rgb[1] = 192;
						rgb[2] = 192;
					}else if (errordiff <= indexlevel16[12] && errordiff > indexlevel16[11]) {
						rgb[0] = 208;
						rgb[1] = 208;
						rgb[2] = 208;
					}else if (errordiff <= indexlevel16[13] && errordiff > indexlevel16[12]) {
						rgb[0] = 224;
						rgb[1] = 224;
						rgb[2] = 224;
					}else if (errordiff <= indexlevel16[14] && errordiff > indexlevel16[13]) {
						rgb[0] = 240;
						rgb[1] = 240;
						rgb[2] = 240;
					}else if (errordiff <= indexlevel16[15] && errordiff > indexlevel16[14]) {
						rgb[0] = 255;
						rgb[1] = 255;
						rgb[2] = 255;
					}
		 	}				 		
						 	blackandwhite.setPixel(x, y+1, rgb);
					 		grayscaleimage.setPixel(x, y+1, grayscalergb);
					 		
					 }
					
					// ### 4
					 if(x+1 < grayscaleimage.getW() && y+1 < grayscaleimage.getH()) {
						 	grayscaleimage.getPixel(x+1, y+1, grayscalergb);
						 	int errordiff = Math.addExact(grayscalergb[0], ((Math.multiplyExact(e, 1)/16)));		 	
						 	
					 		grayscalergb[0] = errordiff;
						 		if(level == 2) {
					 						
					 						// if level is 2 then 0 or 255
									 		if(errordiff >= 127) {
									 			rgb[0] = 255;
									 			rgb[1] = 255;
									 			rgb[2] = 255;
									 		}else {
									 			rgb[0] = 0;
									 			rgb[1] = 0;
									 			rgb[2] = 0;
									 		}
					 			}else if(level == 4){
					 			
				 			           // if level is 4 then 64,128, 192. 255
				 						int[] index = {64,128,192};
							 			if(errordiff <= index[0]) {
											rgb[0] = 64;
											rgb[1] = 64;
											rgb[2] = 64;
										}else if(errordiff <= index[1] && errordiff > index[0]) {
											rgb[0] = 128;
											rgb[1] = 128;
											rgb[2] = 128;
										} else if (errordiff <= index[2] && errordiff > index[1]) {
											rgb[0] = 192;
											rgb[1] = 192;
											rgb[2] = 192;
										}else {
											rgb[0] = 255;
											rgb[1] = 255;
											rgb[2] = 255;
										}
		 					}else if(level == 8){
					 			
				 						if(errordiff <= indexlevel8[0]) {
											rgb[0] = 32;
											rgb[1] = 32;
											rgb[2] = 32;
										}else if(errordiff <= indexlevel8[1] && errordiff > indexlevel8[0]) {
											rgb[0] = 64;
											rgb[1] = 64;
											rgb[2] = 64;
										} else if (errordiff<= indexlevel8[2] && errordiff > indexlevel8[1]) {
											rgb[0] = 96;
											rgb[1] = 96;
											rgb[2] = 96;
										}else if (errordiff <= indexlevel8[3] && errordiff > indexlevel8[2]) {
											rgb[0] = 128;
											rgb[1] = 128;
											rgb[2] = 128;
										}else if (errordiff <= indexlevel8[4] && errordiff > indexlevel8[3]) {
											rgb[0] = 160;
											rgb[1] = 160;
											rgb[2] = 160;
										}else if (errordiff <= indexlevel8[5] && errordiff > indexlevel8[4]) {
											rgb[0] = 192;
											rgb[1] = 192;
											rgb[2] = 192;
										}else if (errordiff <= indexlevel8[6] && errordiff > indexlevel8[5]) {
											rgb[0] = 224;
											rgb[1] = 224;
											rgb[2] = 224;
										}else if (errordiff <= indexlevel8[7] && errordiff > indexlevel8[6]) {
											rgb[0] = 255;
											rgb[1] = 255;
											rgb[2] = 255;
										}
					 		}else if(level == 16){
						 		
					 			if(errordiff <= indexlevel16[0]) {
									rgb[0] = 16;
									rgb[1] = 16;
									rgb[2] = 16;
								}else if(errordiff <= indexlevel16[1] && errordiff > indexlevel16[0]) {
									rgb[0] = 32;
									rgb[1] = 32;
									rgb[2] = 32;
								} else if (errordiff <= indexlevel16[2] && errordiff > indexlevel16[1]) {
									rgb[0] = 48;
									rgb[1] = 48;
									rgb[2] = 48;
								}else if (errordiff <= indexlevel16[3] && errordiff > indexlevel16[2]) {
									rgb[0] = 64;
									rgb[1] = 64;
									rgb[2] = 64;;
								}else if (errordiff <= indexlevel16[4] && errordiff > indexlevel16[3]) {
									rgb[0] = 80;
									rgb[1] = 80;
									rgb[2] = 80;
								}else if (errordiff <= indexlevel16[5] && errordiff > indexlevel16[4]) {
									rgb[0] = 96;
									rgb[1] = 96;
									rgb[2] = 96;
								}else if (errordiff <= indexlevel16[6] && errordiff > indexlevel16[5]) {
									rgb[0] = 112;
									rgb[1] = 112;
									rgb[2] = 112;;
								}else if (errordiff <= indexlevel16[7] && errordiff > indexlevel16[6]) {
									rgb[0] = 128;
									rgb[1] = 128;
									rgb[2] = 128;
								}else if (errordiff <= indexlevel16[8] && errordiff > indexlevel16[7]) {
									rgb[0] = 144;
									rgb[1] = 144;
									rgb[2] = 144;
								}else if (errordiff <= indexlevel16[9] && errordiff > indexlevel16[8]) {
									rgb[0] = 160;
									rgb[1] = 160;
									rgb[2] = 160;
								}else if (errordiff <= indexlevel16[10] && errordiff > indexlevel16[9]) {
									rgb[0] = 176;
									rgb[1] = 176;
									rgb[2] = 176;
								}else if (errordiff<= indexlevel16[11] && errordiff > indexlevel16[10]) {
									rgb[0] = 192;
									rgb[1] = 192;
									rgb[2] = 192;
								}else if (errordiff <= indexlevel16[12] && errordiff > indexlevel16[11]) {
									rgb[0] = 208;
									rgb[1] = 208;
									rgb[2] = 208;
								}else if (errordiff <= indexlevel16[13] && errordiff > indexlevel16[12]) {
									rgb[0] = 224;
									rgb[1] = 224;
									rgb[2] = 224;
								}else if (errordiff <= indexlevel16[14] && errordiff > indexlevel16[13]) {
									rgb[0] = 240;
									rgb[1] = 240;
									rgb[2] = 240;
								}else if (errordiff <= indexlevel16[15] && errordiff > indexlevel16[14]) {
									rgb[0] = 255;
									rgb[1] = 255;
									rgb[2] = 255;
								}
					 	}
					 		blackandwhite.setPixel(x+1, y+1, rgb);
					 		grayscaleimage.setPixel(x+1, y+1, grayscalergb);
					 } 
			}
		}
		
		blackandwhite.write2PPM("errorDiffusion-Level"+level+".ppm");
		blackandwhite.display();
	}
	
  private static void uniformColorQuantization(String imgName) {
  

			  Image imgObj = new Image(imgName);
			  int[] rgb8Q = new int[3];
			  
			  Image newimage8Q = new Image(imgObj.getW(),imgObj.getH());
			  int[] rgb = new int[3];
			  
			  
			  Set<Integer> red = new TreeSet<>();
			  Set<Integer> green = new TreeSet<>();
			  Set<Integer> blue = new TreeSet<>();
			  
			  
			  
			  for(int y=0;y<imgObj.getH();y++){
				for(int x=0;x<imgObj.getW();x++){
			          
					imgObj.getPixel(x, y, rgb);
					
					// find the index and calculate the final value after Quantization
					rgb = getindex(rgb);					
					red.add(rgb[0]);
					green.add(rgb[1]);
					blue.add(rgb[2]);
					
					newimage8Q.setPixel(x, y, rgb);
					
					
					
			      }
			  }
			  
			  // display the LUT 
			  System.out.println("Index\tR\tG\tB");
			  System.out.println("------------------------------");
			  List<innerClass> objects = new ArrayList<>();
						 red.forEach(redval -> {
							 green.forEach(greenval -> {
								 blue.forEach(blueval -> {
									 objects.add(new innerClass(i,redval,greenval,blueval));
									 System.out.print(i++ + "\t" + redval + "\t" + greenval + "\t" + blueval );
									 System.out.println();
								 });
							 });
						 }); 
			  
						 
					String[] name = imgName.split("\\.");
					newimage8Q.write2PPM(name[0]+"-Q8.ppm");
					newimage8Q.display();
			

					for(int y=0;y<imgObj.getH();y++){
						for(int x=0;x<imgObj.getW();x++){
						
							imgObj.getPixel(x, y, rgb8Q);
							
							int valLUT = lutvalues(rgb8Q);
							
							for(int i=0;i<256;i++) {
								if(valLUT == objects.get(i).getNum()){	
									int color = ((rgb8Q[2] - 32) / 64) + (4 * (((rgb8Q[1] - 16) / 32) + (((rgb8Q[0] - 16) / 32) * 8)));
									rgb8Q[0] = color;
									rgb8Q[1] = color;
									rgb8Q[2] = color;
								}
							}
							
							
							imgObj.setPixel(x, y, rgb8Q);
						}
					}
					
					
					imgObj.write2PPM(name[0]+"-index.ppm");
					imgObj.display();
						 
						 
			  
  }
  
  // get the index in the lookup table to get the color values RGB
  public static int[] getindex(int[] values) {
	  
	  
	  
	  int[] b = {64,128,192,256};
	  
	  int redindex = 0;
	  int greenindex=0;
	  int blueindex=0;
	  
	  for(int i=0; i<indexlevel8.length;i++) {
	  if(values[0] <= indexlevel8[i]) { redindex = i; break;}
	  }
	  
	  for(int i=0; i<indexlevel8.length;i++) {
		  if(values[1] <= indexlevel8[i]) { greenindex = i; break;}
		  }
	  
	  for(int i=0; i<b.length;i++) {
		  if(values[2] <= b[i]) { blueindex = i; break;}
	  }
	  
	  values[0] = redindex*32+16;
	  values[1] = greenindex*32+16;
	  values[2] = blueindex*64+32;
	  
	  
	  return values;  // the values of the lookup table for 8bit scale
  }
  
  
  // generate the LUT (Lookup Table ) index from the RGB values
  public static int lutvalues(int[] values) {
	

	  int[] b = {64,128,192,256};
	  
	  int redindex = 0;
	  int greenindex=0;
	  int blueindex=0;

	  for(int i=0; i<indexlevel8.length;i++) {
	  if(values[0] <= indexlevel8[i]) { redindex = i; break;}
	  }
	  
	  for(int i=0; i<indexlevel8.length;i++) {
		  if(values[1] <= indexlevel8[i]) { greenindex = i; break;}
		  }
	  
	  for(int i=0; i<b.length;i++) {
		  if(values[2] <= b[i]) { blueindex = i; break;}
	  }
	  
	  int indexLUT = (bytevaluesforredandgreen[redindex])+(bytevaluesforredandgreen[greenindex]) +(bytevalueforblue[blueindex]);
	  
	  return indexLUT;  // value of the index in 8bit lookup table
  } 
}
	
	
	
	// helper class to save the lookup table in objects
	class innerClass{
		  
		  private int num;
		  private int r;
		  private int g;
		  private int b;
		  
		  innerClass(int number, int red, int green, int blue){
			  this.num = number;
			  this.r = red;
			  this.g = green;
			  this.b = blue;
		  }

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public int getR() {
			return r;
		}

		public void setR(int r) {
			this.r = r;
		}

		public int getG() {
			return g;
		}

		public void setG(int g) {
			this.g = g;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

	  
		  
		  
	  }
	 