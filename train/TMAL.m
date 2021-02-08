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

finNum=round(size(data,1)*0.25);

 
 test_data=data(num+1:end,:);
 

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
 

 
 result(time+1,:)=[mape1 rmse1 mse1];
 
clearvars -EXCEPT result time ;
end








