#include "imagen.h"

int main(int argc, char const *argv[])
{
	unsigned char choice; //Variable en cual se guardara la accion a realizar
	printf("Select a accion to perform \n\t1.-Encrypt\n\t2.-Decrypt\n");
	scanf("%d",&choice);
	if(choice == 1){
		printf("-Encrypt mode selected\n");
		bmpInfoHeader info;
		unsigned char *img;
		//Se elige Texto a cifrar
		char *plaintext = readBytes("WIWYM.txt");
		//printf("length de buffer %d\n",strlen(plaintext) );
		//Se elige imagen a cifrar
		img = abrirBMP("poesia.bmp",&info);
		//printf("Header size = %d,%.4X\n",info.headersize,info.headersize );
		//displayInfo(&info);
		printf("encrypt mode\n");
		encrypt("no.bmp",&info,img,plaintext);
		//guardarBMP("poesia1.bmp",&info,img);
		//printf("TextDisplay\n");
		//TextDisplay(&info,img);
	}else{
		printf("-Decrypt mode selected\n");
		bmpInfoHeader info;
		unsigned char *img;
		img = abrirBMP("no.bmp",&info);
		decrypt("decrypt2.txt",&info,img);
		TextDisplay(&info,img);
	}
	
 	//char *buff = readBytesBMP("cipher.bmp");
 	//printf("Bytes leidos %d\n",sizeof(buff));
	return 0;
}