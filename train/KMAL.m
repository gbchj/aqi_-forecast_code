clear;

for time=0:23
 if(time<10)
  path=strcat('Mspace0501/space05010',num2str(time),'_1.csv')
 else
  path=strcat('Mspace0501/space0501',num2str(time),'_1.csv')
 end
% path=strcat('space0501/space050100_1.csv')
alldata=importdata(path);
odata=alldata.data;
rng(15,'twister');%固定随机种子
t=randperm(size(odata,1));%将数据随机排列
data=odata(t,:);


num=round(size(data,1)*0.70);
init=round(size(data,1)*0.02);

finNum=round(size(data,1)*0.5);
x=round(size(data,1)*0.65);

Ktrain_data=data(1:init,:);
Kutrain_data=data(init+1:num,:);


for i=1:x
    d1=Kutrain_data(:,end);
    [max_s,index]=max(d1);
    Kutrain_data1(i,:)=Kutrain_data(index,:);
    Kutrain_data(index,:)=[];
end

 for i=1:(finNum-init)
     Ktrain_feature = Ktrain_data(:,1:2);%经纬度
     Ktrain_label = Ktrain_data(:,end);%AQI
     
     Kutrain_feature1 = Kutrain_data1(:,1:2);%经纬度
     Kutrain_label1 = Kutrain_data1(:,end);%AQI

     [dmodel, perf] = dacefit(Ktrain_feature,Ktrain_label, @regpoly0, @correxp,2);
     [preuntrain_label mse1]=predictor(Kutrain_feature1,dmodel);
     
     [max_s,index]=max(mse1);
     Ktrain_data(size(Ktrain_data,1)+1,:)=Kutrain_data1(index,:);
     Kutrain_data1(index,:)=[];
 end
     
     
clearvars -EXCEPT Ktrain_data result time data num init finNum;
 
 
 test_data=data(num+1:end,:);
 
 Ktrain_feature = Ktrain_data(:,1:2);%经纬度
 Ktrain_label = Ktrain_data(:,end);%AQI
 
 Ktest_feature = test_data(:,1:2);%经纬度
 Ktest_label = test_data(:,end);%AQI 
     
 [dmodel, perf] = dacefit(Ktrain_feature,Ktrain_label, @regpoly0, @correxp,2);
 [y MSE]=predictor(Ktest_feature,dmodel);
 
 
  %计算均方误差MSE
  
mse= 0;
mape=0;
for i = 1:size(test_data,1)
	mse = mse + (y(i) - Ktest_label(i))^2;
    mape=mape+(abs(y(i)-Ktest_label(i))/Ktest_label(i));
end
mse = mse / size(Ktest_label,1);
rmse=sqrt(mse);
mape=mape/size(Ktest_label,1);

 
 result(time+1,:)=[mape rmse mse]
 
clearvars -EXCEPT result time ;
end







