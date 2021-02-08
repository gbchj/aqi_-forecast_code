clear;
% for time=0:23
% if(time<10)
    time=6
path=strcat('0501shuju\0501_0',num2str(time),'_1.csv');
% else
% path=strcat('0531数据(cluster)\0531_',num2str(time),'_1.csv');
% end
alldata=importdata(path);
data=alldata.data;
allstation=importdata('station1.csv');
station=allstation.data;
rng(15,'twister');%固定随机种子
t=randperm(size(station,1));%将数据随机排列
station= station(t,:);%站点随机排列
fea = station(1:203,2:3);

%MAED主动采样
options = [];
options.KernelType = 'Gaussian';
options.t = 0.5;
options.ReguBeta = 100;
smpRank = MAED(fea,28,options);

for i = 1:size(smpRank,1)
     train_station(i,:)=station(smpRank(i,1),:); 
end

a=1;
for i=1:size(train_station,1)
    for j=1:size(data,1)
        if train_station(i,1)==data(j,1)
           train_data(a,:)=data(j,:);
           a=a+1;
        end
    end
end

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

X=gridsamp([36.459721 113.266111;41.956 119.762],40);
[YX MSE]=predictor(X,dmodel);

%使用gridsamp中与测试数据距离最近的数据作为测试结果
gmse= 0;
gmape=0;
for i=1:size(test_feature,1)
    min=9999;
    s=0;
    for j=1:size(X,1)
        temp=sqrt((test_feature(i,1)-X(j,1))^2+((test_feature(i,2)-X(j,2))^2));%欧拉距离
        if temp<min
            min=temp;
            s=j;
        end
    end
    gmse = gmse + (YX(s) - test_label(i))^2;
    gmape=gmape+(abs(YX(s)-test_label(i))/test_label(i));
end
gmse = gmse / size(test_label,1);
grmse = sqrt(gmse);
gmape=gmape/size(test_label,1);


%使用测试数据测试的结果
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


result(time+1,:)=[gmse mse grmse rmse gmape mape];

% clearvars -EXCEPT result time ;

% end



