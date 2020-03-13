<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="js/echarts.js"></script>
    <script src="js/jquery-1.10.2.min.js"></script>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: 'Java工程师各岗位平均薪资'
            },
            tooltip: {},
            legend: {
                data:['平均薪资']
            },
            xAxis: {
                data: ["JAVA高级工程师", "JAVA见习工程师", "JAVA软件工程师", "JAVA中级工程师"]
            },
            yAxis: {},
            series: [{
                name: '平均薪资',
                type: 'bar',
                data: [13000.000000, 5950.000000, 10189.743590, 9500.000000]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        
        //发起异步请求获取dao层数据
        $.ajax({
        	type:"post",//请求类型
        	url:"JobServlet",//请求路径
        	success:function(jobAvgSals){
        		myChart.setOption({
        			xAxis:{
        				data:jobAvgSals.jobName
        			},
        			series:[{
        				name:'平均薪资',
        				type:'bar',
        				data:jobAvgSals.avgSal
        			}]
        		});
        	}
        });
        
    </script>
</body>
</html>
