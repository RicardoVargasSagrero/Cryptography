#include <stdio.h>
/*Ricardo Vargas Sagrero
Algorithm for calculating the modular exponentiation
source: http://www.dma.fi.upm.es/recursos/aplicaciones/matematica_discreta/web/aritmetica_modular/ExpoModRapida.html
exp  = a^n mod m
Restrictions 
0 <= a < m,  m >= 2,  n >=0 
*/
int exponent(long int, int,int);
int main(){
	long int number,result;
	int base;
	int module;
	printf("\tAlgorithm for calculating the modular exponentiation\n\texp = a^n mod m\n");
	printf("Enter the number a = \n");
	scanf("%ld",&number);
	printf("Enter the exponent n = \n");
	scanf("%d",&base);
	printf("Enter the module m = \n");
	scanf("%d",&module);
	result = exponent(number,base,module);
	printf("Result = %ld\n",result);
	return 0; 
}
int exponent(long int a, int n,int m){
	int exp = 1;
	long int x = a % m;
	while((n > 0) & x > 1){
		if(n & 1){
			exp = (exp*x) % m;
		}
		x = (x*x) % m;
		n = n/2;
	}
	return exp;
}