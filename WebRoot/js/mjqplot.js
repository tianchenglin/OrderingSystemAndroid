/***
 *	作者：DevilJie 
 *	创建时间：2014-11-05
 *	个人网站：http://www.w3210.com
 **/
var j = {jqplot :{}};
j.jqplot.diagram = {
	/**
	 * document: 输出图形的位置id
	 * s：柱状图数据 例如：[[1,2,3,4]]单柱状图  [[1,2,3,4],[2,3,4,5]] 双柱状图 以此类推
	 * title：每一个柱状对应的名称 ["参加人数","中奖人数"]
	 * ticks:x轴显示数据例如[1,2,3,4,5,6,7,8]
	 * x_label:x轴名称
	 * y_label:y轴名称
	 * t: 1：曲线图 2：柱状图
	 */
	base : function(document, s, xtitle, title, ticks, x_label, y_label, max){
        var plot3 = $.jqplot(document, s, {
        	title: title,
			animate:true,
			seriesDefaults: {  
               renderer: $.jqplot.BarRenderer, // 利用渲染器（BarRenderer）渲染现有图表  
               pointLabels: { show: false }  
            },
			axes:{
				yaxis:{
            		label: y_label==null?"":y_label,
            		max:max
				},
				xaxis:{
					renderer: $.jqplot.CategoryAxisRenderer, // 设置横（纵）轴上数据加载的渲染器,
					ticks: ticks,
                	label: x_label==null?"":x_label
				}
			},
			series:[{color:'#d0d0d0'}] ,
			
        });
	},
	
	bing : function(document,title, s1){
		
		var plot1 = $.jqplot(document, s1, {
			title: title,
			seriesDefaults: { 
		    fill: false,    
            showMarker: false,    
            shadow: false,    
            renderer:$.jqplot.PieRenderer,    
            rendererOptions:{    
                diameter: undefined, // 设置饼的直径    
                padding: 20, // 饼距离其分类名称框或者图表边框的距离，变相该表饼的直径    
                sliceMargin: 5, // 饼的每个部分之间的距离    
                showDataLabels: true,
                fill:true, // 设置饼的每部分被填充的状态    
                shadow:true, //为饼的每个部分的边框设置阴影，以突出其立体效果    
                shadowOffset: 2, //设置阴影区域偏移出饼的每部分边框的距离    
                shadowDepth: 5, // 设置阴影区域的深度    
                shadowAlpha: 0.07 // 设置阴影区域的透明度    
            }    
			},
			series:['#f7f7f7','#5dacd3']
        });
	}
};