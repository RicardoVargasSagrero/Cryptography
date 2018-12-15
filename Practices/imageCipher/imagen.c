/* Referencia:
   https://poesiabinaria.net/2011/06/leyendo-archivos-de-imagen-en-formato-bmp-en-c/
*/

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include "imagen.h"

unsigned char *abrirBMP (char *filename, bmpInfoHeader * bInfoHeader){

  FILE *f;
  bmpFileHeader header;		/* cabecera */
  unsigned char *imgdata;	/* datos de imagen */
  uint16_t type;		/* 2 bytes identificativos */

  f = fopen (filename, "r");
  if (f == NULL)
    {
      perror ("Error al abrir el archivo en lectura");
      exit (EXIT_FAILURE);
    }

  /* Leemos los dos primeros bytes */
  fread (&type, sizeof (uint16_t), 1, f);
  //printf ("%.4X\n", type);
  if (type != 0x4D42)		/* Comprobamos el formato */
    {
      printf ("Error en el formato de imagen, debe ser BMP de 24 bits\n");
      fclose (f);
      printf ("After the IF, \n");
      return NULL;
    }

  /* Leemos la cabecera de fichero completa */
  fread (&header, sizeof (bmpFileHeader), 1, f);

  /* Leemos la cabecera de información completa */
  fread (bInfoHeader, sizeof (bmpInfoHeader), 1, f);

  /* Reservamos memoria para la imagen, ¿cuánta?
     Tanto como indique imgsize */
  imgdata = (unsigned char *) malloc (bInfoHeader->imgsize);
  if (imgdata == NULL)
    {
      perror ("Error al asignar memoria");
      exit (EXIT_FAILURE);
    }
  /* Nos situamos en el sitio donde empiezan los datos de imagen,
     nos lo indica el offset de la cabecera de fichero */
  fseek (f, header.offset, SEEK_SET);

  /* Leemos los datos de imagen, tantos bytes como imgsize */
  fread (imgdata, bInfoHeader->imgsize, 1, f);

  /* Cerramos el apuntador del archivo de la imagen */
  fclose (f);

  /* Devolvemos la imagen */
  return imgdata;
}

void guardarBMP (char *filename, bmpInfoHeader * info, unsigned char *imgdata){
  bmpFileHeader header;
  FILE *f;
  uint16_t type;
  
  f=fopen(filename, "w+");
  if(f == NULL)
    perror("Error al abrir el archivo");
  header.size=info->imgsize+sizeof(bmpFileHeader)+sizeof(bmpInfoHeader);
  /* header.resv1=0; */
  /* header.resv2=1; */
  /* El offset será el tamaño de las dos cabeceras + 2 (información de fichero)*/
  header.offset=sizeof(bmpFileHeader)+sizeof(bmpInfoHeader)+2;
  /* Escribimos la identificación del archivo */
  type=0x4D42;
  fwrite(&type, sizeof(type),1,f);
  /* Escribimos la cabecera de fichero */
  fwrite(&header, sizeof(bmpFileHeader),1,f);
  /* Escribimos la información básica de la imagen */
  fwrite(info, sizeof(bmpInfoHeader),1,f);
  /* Escribimos la imagen */
  fwrite(imgdata, info->imgsize, 1, f);
  fclose(f);
}

void encrypt (char *filename, bmpInfoHeader * info, unsigned char *img,char *plaintext){
  int j = 0,i = 0;
  unsigned char auxBits = 7; //Esta variable sirve para contar el corrimiento de bits
  printf("Plaintext bytes %d\n",strlen(plaintext) );
  for(j = 0, i = 0; (j < strlen(plaintext)) && i < info->imgsize;auxBits--,i++){
    if(auxBits == 0){
      //printf("%.2X ",img[i]);
      img[i] = img[i] ^ ((plaintext[j] >> auxBits) & 0x01);
      printf("%.2X ",img[i]);
      j++;
      auxBits = 8;
    }else{
      //printf("%.2X ",img[i]);
      img[i] = img[i] ^ ((plaintext[j] >> auxBits) & 0x01);
      printf("%.2X ",img[i]);
    }
  }
  /*for(i = 0; i < info->imgsize; i++)
    img[i] = img[i] ^ 0xff;*/
  j = info->imgsize;
  printf ("\n%d\n", j);
  bmpFileHeader header;
  FILE *f;
  uint16_t type;
  f = fopen (filename, "w+");
  if (f == NULL){
      perror ("Error al abrir el archivo en escritura");
      exit (EXIT_FAILURE);}
  header.size =
    info->imgsize + sizeof (bmpFileHeader) + sizeof (bmpInfoHeader);
  /* header.resv1=0; */
  /* header.resv2=1; */
  /* El offset será el tamaño de las dos cabeceras + 2 (información de fichero) */
  header.offset = sizeof (bmpFileHeader) + sizeof (bmpInfoHeader) + 2;
  /* Escribimos la identificación del archivo */
  type = 0x4D42;
  fwrite (&type, sizeof (type), 1, f);
  /* Escribimos la cabecera de fichero */
  fwrite (&header, sizeof (bmpFileHeader), 1, f);
  /* Escribimos la información básica de la imagen */
  fwrite (info, sizeof (bmpInfoHeader), 1, f);
  /* Escribimos la imagen */
  fwrite (img, info->imgsize, 1, f);
  fclose (f);
}
void decrypt (char *filename, bmpInfoHeader * info, unsigned char *img){
  char *plaintext = malloc(3000 * sizeof(char));
  int j = 0,i = 0;
  plaintext[0] = 0x01;
  printf("%d\n",strlen(plaintext) );
  unsigned char auxBits = 7; //Esta variable sirve para contar el corrimiento de bits
  for(j = 0, i = 0;i < info->imgsize & j < strlen(plaintext)+1;auxBits--,i++){
    if(auxBits == 0){
      //printf("(%d)",((plaintext[j] >> auxBits) & 0x01) );
      plaintext[j] = plaintext[j] ^ ((img[i] & 0x01) << auxBits);
      //printf("%.2X (%d) [%d] ",img[i],((img[i] & 0x01) << auxBits),auxBits);
      //printf("\n%.2X\n ",plaintext[j] );
      /*if(plaintext[j] == '#')
        flag++;
      if(flag == 2)
        break;*/
      j++;
      auxBits = 8;
    }else{
      if(auxBits == 7)
        plaintext[j] = 0x00;
      //printf("%.2X (%d) [%d] ",img[i],((img[i] & 0x01) << auxBits),auxBits);
      plaintext[j] = plaintext[j] ^ ((img[i] & 0x01) << auxBits);
      //corrimiento = corrimiento >> 1;
    }
  }
  printf("\nPRINTING Plaintext %d \n",strlen(plaintext));
  for(i = 0; i < strlen(plaintext); i++){
    printf("%c ",plaintext[i]);
  }
  FILE *f;
  f = fopen(filename,"w+");
  if (f == NULL){
    perror ("Error al abrir el archivo en escritura");
    exit (EXIT_FAILURE);
  }
  fwrite (plaintext, strlen(plaintext), 1, f);
  free(plaintext);
  fclose (f);
}
void displayInfo (bmpInfoHeader * info){
  printf ("Tamaño de la cabecera: %u\n", info->headersize);
  printf ("Anchura: %d\n", info->width);
  printf ("Altura: %d\n", info->height);
  printf ("Planos (1): %d\n", info->planes);
  printf ("Bits por pixel: %d\n", info->bpp);
  printf ("Compresión: %d\n", info->compress);
  printf ("Tamaño de datos de imagen: %u\n", info->imgsize);
  printf ("Resolucón horizontal: %u\n", info->bpmx);
  printf ("Resolucón vertical: %u\n", info->bpmy);
  printf ("Colores en paleta: %d\n", info->colors);
  printf ("Colores importantes: %d\n", info->imxtcolors);
}

void saveCipher (bmpInfoHeader * info, unsigned char *img){
  int x, y;
  /* Reducimos la resolución vertical y horizontal para que la imagen entre en pantalla */
  static const int reduccionX = 6, reduccionY = 4;
  /* Si la componente supera el umbral, el color se marcará como 1. */
  static const int umbral = 90;
  /* Asignamos caracteres a los colores en pantalla */
  static unsigned char colores[9] = " bgfrRGB";
  int r, g, b;

  /* Dibujamos la imagen */
  for (y = info->height; y > 0; y -= reduccionY)
    {
      for (x = 0; x < info->width; x += reduccionX)
	{
	  b = (img[3 * (x + y * info->width)] > umbral);
	  g = (img[3 * (x + y * info->width) + 1] > umbral);
	  r = (img[3 * (x + y * info->width) + 2] > umbral);

	  printf ("%c", colores[b + g * 2 + r * 4]);
	}
      printf ("\n");
    }
}

void TextDisplay (bmpInfoHeader * info, unsigned char *img){
  int x, y;
  /* Reducimos la resolución vertical y horizontal para que la imagen entre en pantalla */
  static const int reduccionX = 6, reduccionY = 4;
  /* Si la componente supera el umbral, el color se marcará como 1. */
  static const int umbral = 90;
  /* Asignamos caracteres a los colores en pantalla */
  static unsigned char colores[9] = " bgfrRGB";
  int r, g, b;

  /* Dibujamos la imagen */
  for (y = info->height; y > 0; y -= reduccionY)
    {
      for (x = 0; x < info->width; x += reduccionX)
	{
	  b = (img[3 * (x + y * info->width)] > umbral);
	  g = (img[3 * (x + y * info->width) + 1] > umbral);
	  r = (img[3 * (x + y * info->width) + 2] > umbral);

	  printf ("%c", colores[b + g * 2 + r * 4]);
	}
      printf ("\n");
    }
}            

char *readBytes (char *filename){
  //This funtion open a File and reads all the of their bytes
  FILE *fileptr;
  char *buffer;
  long filelen;

  fileptr = fopen (filename, "rb");	// Open the file in binary mode
  fseek (fileptr, 0, SEEK_END);	// Jump to the end of the file
  filelen = ftell (fileptr);	// Get the current byte offset in the file
  rewind (fileptr);		// Jump back to the beginning of the file

  buffer = (char *) malloc ((filelen + 1) * sizeof (char));	// Enough memory for file + \0
  fread (buffer, filelen, 1, fileptr);	// Read in the entire file
  fclose (fileptr);		// Close the file
  return buffer;
}

char *readBytesBMP (char *filename){
  //This funtion open a File and reads all the of their bytes
  FILE *fileptr;
  char *buffer;
  long filelen;

  fileptr = fopen (filename, "rb");	// Open the file in binary mode
  fseek (fileptr, 0, 52);	// Jump to the end of the file
  filelen = ftell (fileptr);	// Get the current byte offset in the file
  rewind (fileptr);		// Jump back to the beginning of the file

  buffer = (char *) malloc ((filelen + 1) * sizeof (char));	// Enough memory for file + \0
  fread (buffer, filelen, 1, fileptr);	// Read in the entire file
  fclose (fileptr);		// Close the file
  return buffer;
}
