/*
  Name: sudoku
  Copyright: 1.0
  Author: Jayin Ton
  Date: 08/12/12 16:11
  Description:������ (�����)
  Language:C++
*/

#include<iostream>
#include<fstream>
#include<cstring>
#include<ctime>
#include<stdlib.h>
#include<string>
#define ROW     9  // 9��
#define COLUMN  9  // 9��
using namespace std;

struct rec            //�㣨x,y�����������1-9
{   int x;
    int y;
	int amount;
	int team[ROW+1];     //
}list[ROW * COLUMN+1];     //�����б�

const int module[ROW+1][COLUMN+1]={ 0,0,0,0,0,0,0,0,0,0,
                                     0,1,1,1,2,2,2,3,3,3,
                                     0,1,1,1,2,2,2,3,3,3,
							         0,1,1,1,2,2,2,3,3,3,
							         0,4,4,4,5,5,5,6,6,6,
  				     		         0,4,4,4,5,5,5,6,6,6,
							         0,4,4,4,5,5,5,6,6,6,
							         0,7,7,7,8,8,8,9,9,9,
								     0,7,7,7,8,8,8,9,9,9,
								     0,7,7,7,8,8,8,9,9,9,} ;      //9��3*3С����
int cur_map        [ROW + 1][COLUMN + 1];     //��ǰ�����ľ���
bool row           [ROW + 1][COLUMN + 1];      //��״̬ 
bool column        [ROW + 1][COLUMN + 1];     //��״̬ 
bool block         [ROW + 1][COLUMN + 1];     //3*3ģ��״̬ 
int tail=0;             //list����

ifstream fin("data.txt");
ofstream  fout("result.txt");
unsigned long start_sec,ending_sec;   
string start_time,ending_time;

void init();
void fresh();  
void swap(int&,int&);  
void quick_sort(int,int);
void print();
void solve(int );
void get_time(int);

int main()
{   get_time(1);
    init();                   //���� 
    fresh();                 //ɸѡ��ÿ���ո�Ŀ�������� 
	quick_sort(1,tail);     //�������� ���ո���������������������С�ŵ��󣬣�PS:��81���� �е���С�á����� 
	solve(1);               //��ʼ���� 
    return 0;
}

void init()
{   int i,j;
	memset(   row,    false,sizeof(row));  //��ʼ��
	memset(   column, false,sizeof(column));
	memset(   block,  false,sizeof(block));
    for(i = 1 ; i <= ROW ; i++)
    {
     for( j = 1 ; j<= COLUMN ;  j++)
	 { 
   	     int x;
         fin>>x;
		 cur_map[i][j] = x ;
		 if(x != 0)
		 {
		      row[i][x] = true;
		      column[j][x] = true;
		      block[ module[i][j] ][x] = true;
		   }
	  } 
	 } 
	 fin.close();
}

void fresh()
{ 
   int i,j;
   for(i = 1; i <= ROW ; i++)
     for (j = 1; j <= COLUMN ; j++)
	   if(cur_map[i][j] == 0)
	   {  
	      tail++;             //���
		  list[tail].x = i;
		  list[tail].y = j;
	      int k;
		  bool p[ROW+1];
		  memset(p,false,sizeof(p));
		  for(k = 1 ; k <= ROW ; k++ ) if(cur_map[k][j] != 0)       p[cur_map[k][j]]=true;  //����������
		  for(k = 1 ; k <= COLUMN;k++) if(cur_map[i][k] != 0)       p[cur_map[i][k]]=true;  //����������
		  for(k = 1 ; k <= ROW ; k++ ) if(!block[module[i][j]][k])  p[cur_map[i][k]]=true;  //��ģ��������
		  for(k = 1 ; k <= ROW ; k++)  if(!p[k]){
                                                      int t=++list[tail].amount;		  //�������
													  list[tail].team[t]=k;
													 }
 	   }
}

void print()
{  int i , j;
   for ( i = 1 ; i <= ROW ; i++){
     for ( j = 1 ; j<= COLUMN ; j++)
	   fout<<cur_map[i][j]<<" ";
	 fout<<endl;
	}
  get_time(2);
  unsigned long temp=ending_sec-start_sec;
  fout<<endl; 
  fout<<"Start at:"<<start_time<<endl;
  fout<<"Finish at:  "<<ending_time<<endl;
  fout<<"It wastes you "<<temp<<"s."<<endl;  
  if(temp <= 2) fout<<"It's easy job!";
    else if(temp <= 6) fout<<"It's a changlle!";
      else fout<<"It's too hard for you!";
  fout.close();
  exit(0);
}

void solve(int head)   //headΪ��ǰ�ĵ㣨X,y��
{  srand((unsigned)time(0));  
   if( head  > tail ) print();
   else 
   { // cout<<head<<endl;
      int j;
      bool visit[ROW+1];
      memset(visit,false,sizeof(visit));
      visit[0]=true;
	  for ( j = 1 ; j <= list[head].amount ; j++)
	  {  int i=0;
	     while(visit[i]) i = 1 + rand() % list[head].amount;
	     visit[i]=true;
         int x,y,v,m;
	     x=list[head].x;
		 y=list[head].y;
		 v=list[head].team[i];
		 m=module[x][y];
		// cout<<x<<" "<<y<<" "<<v<<" "<<m<<endl;
		 if(!row[x][v] && !column[y][v] && !block[m][v] )  //�Ƿ����V
		 {   cur_map[x][y]   =v;
		     row[x][v]       =true;
			 column[y][v]    =true;
			 block[m][v]     =true;
			 solve(head+1);
			 cur_map[x][y]   =0;
		     row[x][v]       =false;
			 column[y][v]    =false;
			 block[m][v]     =false;
			 
		 }
	   }
   }
}

void swap(int &x,int &y)
{ 
  int t=x;x=y;y=t;
}

void quick_sort(int l, int r )  //sort
{
   int i=l,j=r,mid=list[(l+r) / 2].amount;
   do
   {  
      while (list[i].amount < mid) i++;  //change
	  while (list[j].amount > mid) j--;
	  if(i<=j)
	  {   int k,max;
	      max= list[i].amount > list[j].amount ? list[i].amount:list[j].amount;
		  for(k = 1 ; k <= max ; k++ )swap(list[i].team[k],list[j].team[k]);
	      swap(list[i].amount,list[j].amount);
		  swap(list[i].x,list[j].x);
		  swap(list[i].y,list[j].y);
		  i++;
		  j--;
	  }
   }while (i<=j);
   if(i < r) quick_sort(i,r);
   if(j > l) quick_sort(l,j);
}

void get_time(int syb)
{
    time_t now_time;         //ʱ������
    struct tm *local_time;    //ʱ��ṹ
    if(syb == 1) start_sec = time (&now_time );      //��ȡʱ��
           else ending_sec = time (&now_time );
    local_time = localtime ( &now_time );    //ת��Ϊ����ʱ��
    if(syb == 1) start_time = asctime (local_time); 
        else    ending_time = asctime (local_time);
 }














