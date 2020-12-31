# FinalWork
This is the final work of Lijie Lv

#如果我要取第二页学生表的数据（每页显示10条）
#Select * From Student order by studentID  Limit 10(PageSize) Offset 10 ((PageCurrent-1)*PageSize);
#以上语句表示从Student表获取数据，跳过10条，取10行 。
#也可以这样写 select * from Student order by studentID  limit10,10
#和上面的的效果一样
#公式: sql = "select * from tableName where "+条件+" order by "+排序+" limit "+要显示多少条记录+" offset "+跳过多少条记录;
