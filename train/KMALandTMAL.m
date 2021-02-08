clear;

for time=0:23
 if(time<10)
  path=strcat('space0501/space05010',num2str(time),'_1.csv')
 else
  path=strcat('space0501/space0501',num2str(time),'_1.csv')
 end

alldata=importdata(path);
odata=alldata.data;
rng(15,'twister');%固定随机种子
t=randperm(size(odata,1));%将数据随机排列
data=odata(t,:);

num=round(size(data,1)*0.70);
init=round(size(data,1)*0.02);

finNum=round(size(data,1)*0.1);

Ktrain_data=data(1:init,:);
Kutrain_data=data(init+1:num,:);

 for i=1:(finNum-init)
     Ktrain_feature = Ktrain_data(:,1:2);%经纬度
     Ktrain_label = Ktrain_data(:,end);%AQI
     
     Kutrain_feature = Kutrain_data(:,1:2);%经纬度
     Kutrain_label = Kutrain_data(:,end);%AQI

     [dmodel, perf] = dacefit(Ktrain_feature,Ktrain_label, @regpoly0, @correxp,2);
     [preuntrain_label mse1]=predictor(Kutrain_feature,dmodel);
     
     [max_s,index]=max(mse1);
     Ktrain_data(size(Ktrain_data,1)+1,:)=Kutrain_data(index,:);
     Kutrain_data(index,:)=[];
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

nTree =50;
 Ttrain_data=data(1:init,:);
 
 Tutrain_data=data(init+1:num,:);
 
 
 for b=1:(finNum-init)
   
     Ttrain_feature = Ttrain_data(:,4:end-1);%经纬度
     Ttrain_label = Ttrain_data(:,end);%AQI
      
  Tutrain_feature = Tutrain_data(:,4:end-1);%经纬度
     Tutrain_label = Tutrain_data(:,end);%AQI
     
     Factor1 = TreeBagger(nTree, Ttrain_feature, Ttrain_label,'Method','regression');
[y1,Scores1] = predict(Factor1, Ttrain_feature);
mse1= 0;
for i = 1:size(Ttrain_label,1)
	mse1= mse1 + (y1(i) - Ttrain_label(i))^2;
end 
mse1 = mse1 / size(Ttrain_label,1);
[y2,Scores2] = predict(Factor1, Tutrain_feature);
   
s=[];
for(j=1:size(Tutrain_data,1))
     Ttrain_label1(size(Ttrain_data,1)+1,:) = y2(j,:);%AQI
       Ttrain_feature1(size(Ttrain_data,1)+1,:) =Tutrain_feature(j,:);%经纬度
       
       Factor2 = TreeBagger(nTree, Ttrain_feature1, Ttrain_label1,'Method','regression');
       [y3 Socres3]=predict(Factor2, Ttrain_feature); 
    mse=0;
    for a = 1:size(Ttrain_label,1)
	mse = mse + (y3(a) - Ttrain_label(a))^2;
    end
    mse = mse / size(Ttrain_label,1);
    s(j,1)=abs(mse-mse1);
end
 [max_s,index]=max(s);
       Ttrain_data(size(Ttrain_data,1)+1,:)=Tutrain_data(index,:);
       Tutrain_data(index,:)=[];
      
 end

    Ttrain_feature = Ttrain_data(:,4:end-1);%经纬度
     Ttrain_label = Ttrain_data(:,end);%AQI
    
 Ttest_feature = test_data(:,4:end-1);%经纬度
 Ttest_label = test_data(:,end);%AQI 

 Factor = TreeBagger(nTree, Ttrain_feature, Ttrain_label,'Method','regression');
[z,Scores] = predict(Factor, Ttest_feature);

mse1= 0;
mape1=0;
for c = 1:size(test_data,1)
	mse1 = mse1 + (z(c) - Ttest_label(c))^2;
    mape1=mape1+(abs(z(c)-Ttest_label(c))/Ttest_label(c));
end
 mse1 = mse1 / size(Ttest_label,1);
 rmse1=sqrt(mse1);
 mape1=mape1/size(Ttest_label,1);
 
 X=[ones(length(Ktest_label),1),y,z];
 [b,bint,r,rint,stats]=regress(Ttest_label,X);

 w=b(1)+b(2)*y+b(3)*z;
 plot(X,Ttest_label,'k+',X,w,'r')
 mse2= 0;
mape2=0;
for c = 1:size(test_data,1)
	mse2 = mse2 + (w(c) - Ttest_label(c))^2;
    mape2=mape2+(abs(w(c)-Ttest_label(c))/Ttest_label(c));
end
 mse2 = mse2/ size(Ttest_label,1);
 rmse2=sqrt(mse2);
 mape2=mape2/size(Ttest_label,1);
 
 result(time+1,:)=[mape rmse mse mape1 rmse1 mse1 mape2 rmse2 mse2];
 
clearvars -EXCEPT result time ;
end








