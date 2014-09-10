/*
  Name: sudokucheck 
  Copyright: 1.0
  Author: Jayin Ton 
  Date: 08/12/12 16:12
  Description: 判断你所做的数独是否正确！ 
  Language:C++
*/

#include<iostream>
#include<fstream>
#include<cstring>
#include<time.h>
#include<string>
#define ROW     9  // 9行
#define COLUMN  9  // 9列
using namespace std;

struct rec            //点（x,y）待填的数字1-9
{   int x;
    int y;
	int amount;
	int team[ROW+1];     //
}list[ROW * COLUMN+1];     //待填列表

const int module[ROW+1][COLUMN+1]={ 0,0,0,0,0,0,0,0,0,0,
                                     0,1,1,1,2,2,2,3,3,3,
                                     0,1,1,1,2,2,2,3,3,3,
							         0,1,1,1,2,2,2,3,3,3,
							         0,4,4,4,5,5,5,6,6,6,
  				     		         0,4,4,4,5,5,5,6,6,6,
							         0,4,4,4,5,5,5,6,6,6,
							         0,7,7,7,8,8,8,9,9,9,
								     0,7,7,7,8,8,8,9,9,9,
								     0,7,7,7,8,8,8,9,9,9,} ;      //9个3*3小宫格
int cur_map        [ROW + 1][COLUMN + 1];     //当前数独的矩阵
bool row           [ROW + 1][COLUMN + 1];      //行状态 
bool column        [ROW + 1][COLUMN + 1];     //列状态 
bool block         [ROW + 1][COLUMN + 1];     //3*3模块状态 
int tail=0;             //list长度

ifstream fin("result.txt");

unsigned long start_sec,ending_sec;   
string start_time,ending_time;
int tot=0;
 
void init();
void print();

int main()
{  
    init();                   //输入 
    if(tot == 81 )cout<<"Correct Answer！";
      else cout<<"not finish yet or input error!" ;
    getchar();
    return 0;
}


void init()
{   int i,j;
	memset(   row,    false,sizeof(row));  //初始化
	memset(   column, false,sizeof(column));
	memset(   block,  false,sizeof(block));
    for(i = 1 ; i <= ROW ; i++)
    {
     for( j = 1 ; j<= COLUMN ;  j++)
	 { 
   	     int x;
         fin>>x;
		 cur_map[i][j] = x ;
		 if(x <= 0 || x > 9){ cout<<"input error!";exit(0);}
		 if(x != 0)
		 {    tot++;
             int mm=module[i][j];
		     if(!row[i][x])         row[i][x] = true; else print();
		     if(!column[j][x])   column[j][x] = true; else print();
		     if(!block[mm][x])   block[mm][x] = true; else print();
		   }
	  } 
	 } 
	 fin.close();
}

void print()
{
   cout<<"Wrong Answer！";
   fin.close();
   getchar();
   exit(0);
}



















