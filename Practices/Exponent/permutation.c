#include <stdio.h>
#include <stdlib.h>

int main(){
	unsigned char data[2]={0x68, 0x69};
	unsigned char output[2]={0x00, 0x00};
	printf("%x %x \n",data[0],data[1]);
	//printf("\n%c %c",output[0],output[1]);
	unsigned char p[16]={3,13,5,6,12,0,7,15,4,8,1,11,9,10,14,2};
	unsigned char i,j,res,pos,cnt=0;
	for(j=0;j<16;j++){
		//printf("%d\n", p[j]);
		res=p[j]>>3;
		pos=p[j]&7;
		//printf("%d %d %d\n",p[j],res,pos);
		//printf("%d \n",1<<pos);
		//for(i=1;i<128;i<<=1){                   
			//printf("%d\n",i);
			//printf("%d\n",(1<<cnt));
			if(data[res] & (1<<pos)){
				//printf("\n%d & %d = %d",data[res],(1<<pos),data[res] & (1<<pos));
				//printf("1");
				
				
				output[j>>3]=output[j>>3]|1<<cnt;
				
				//printf()
			}
			if(cnt==7)
				cnt=0;
			else
				cnt++;
			//else
				//printf("0");
		//}
	}
	//printf("%x %x",data[0],data[1]);
	printf("\n0x%x 0x%x\n",output[0],output[1]);
	return 0;
}
