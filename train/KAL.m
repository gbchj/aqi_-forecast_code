clear;

% for time=0:23
% if(time<10)
time=6
path=strcat('0501shuju/0501_0',num2str(time),'_1.csv');
% else
% path=strcat('0501shuju/0501_',num2str(time),'_1.csv');
% end

alldata=importdata(path);
data=alldata.data;
allstation=importdata('station1.txt');
rng(15,'twister');%固定随机种子
t=randperm(size(allstation,1));%将数据随机排列
station= allstation(t,:);%站点随机排列
train_station=station(1:4,:);
untrain_station=station(5:203,:);

a=1;
for i=1:size(train_station,1)
    for j=1:size(data,1)
        if train_station(i,1)==data(j,1)
           train_data(a,:)=data(j,:);
           a=a+1;
        end
    end
end

c=1;
for i=1:size(untrain_station,1) 
    for j=1:size(data,1) 
        if untrain_station(i,1)==data(j,1)
           untrain_data(c,:)=data(j,:);
           c=c+1; 
        end
    end
end

 for i=1:20
     train_feature = train_data(:,2:3);%经纬度
     train_label = train_data(:,4);%AQI
     
     untrain_feature = untrain_data(:,2:3);%经纬度
     untrain_label = untrain_data(:,4);%AQI

     
     [dmodel, perf] = dacefit(train_feature,train_label, @regpoly0, @correxp,2);
     [preuntrain_label mse1]=predictor(untrain_feature,dmodel); 
     
     
     
    
      [max_s,index]=max(mse1);
      res(i,:)=untrain_data(index,:);
      train_data(size(train_data,1)+1,:)=untrain_data(index,:);
      untrain_data(index,:)=[];
 end
 
%  clearvars -EXCEPT train_data  station data result time;
 test_station=station(204:end,:);
b=1;
for i=1:size(test_station,1)
    for j=1:size(data,1)
        if test_station(i,1)==data(j,1)
           test_data(b,:)=data(j,:);
           b=b+1;
        end
    end
end

     train_feature = train_data(:,2:3);%经纬度
     train_label = train_data(:,4);%AQI
  
     test_feature = test_data(:,2:3);%经纬度
     test_label = test_data(:,4);%AQI
     
     [dmodel, perf] = dacefit(train_feature,train_label, @regpoly0, @correxp,2);
     [y MSE]=predictor(test_feature,dmodel);
     
     %计算均方误差MSE
mse= 0;
mape=0;
for i = 1:size(test_label,1)
	mse = mse + (y(i) - test_label(i))^2;
    mape=mape+(abs(y(i)-test_label(i))/test_label(i));
end;
mse = mse / size(test_label,1);
rmse=sqrt(mse);
mape=mape/size(test_label,1);

result(time+1,:)=[mape rmse mse];

% clearvars -EXCEPT result time ;

% end





