(function($){
	//默认实现：根据返回数据， 构建html
	function buildSug(data, $keywordCtrl, $sugCtrl, options){
		var rentOrSoldText = options.pageType == 'zufang'  || options.pageType == 'zufangditu' ? '在租' : '在售';

		var listItem;
		if(data && data.length>0){
			$.each(data, function(index, node){
				var keyword = $.trim($keywordCtrl.val());
				var name = options.pageType=='xinfang'?this.nameShow:this.showName;
				var showName = name.replace(keyword, '<b '+(options.pageType=='xinfang'?'class="red"':'')+'>'+keyword+'</b>');
				if(options.pageType == "xinfang"){
					var villaNameTag, priceTag = "";
					if(this.type == "block" || this.type == "blockAlias"){//是楼盘才有类型和价格标签
						villaNameTag = this.villaName;
						if(this.villaName != "别墅"){
							priceTag = (this.price && this.price!=0) ? '均价<span class="price_xinfang">'+this.price+'</span>元/平' : '均价待定';
						}
						else{
							priceTag = (this.price && this.price!=0) ? '总价<span class="price_xinfang">'+this.price+'</span>万起' : '总价待定'
						}
					}
					listItem = $('<li class="actSelect ui-menu-item" key="'+this.url+'" gahref="search_suggestion_click_order_'+(index+1)+'"><span class="left hot-title"><i>'+showName+'</i>'+(villaNameTag?'<span class="type-text">'+villaNameTag+'</span>':'')+'</span><span class="count right">'+priceTag+'</span></li>'+((index != data.length-1)?'<li class="ui-menu-line"></li>':''));
				}
				else if(options.pageType == "fangjia"){
					listItem = $('<li class="actSelect ui-menu-item" key="'+this.url+'" gahref="search_suggestion_click_order_'+(index+1)+'"><span class="left hot-title"><i>'+showName+'</i></span></li>'+((index != data.length-1)?'<li class="ui-menu-line"></li>':''));
				}else{
					listItem = $('<li class="actSelect ui-menu-item" key="'+this.url+'" gahref="search_suggestion_click_order_'+(index+1)+'"><span class="left hot-title"><i>'+showName+'</i><span class="sub-text"> '+this.remark+'</span></span><span class="count right">约&nbsp;'+this.sellCount+'<b>套'+rentOrSoldText+'</b></span></li>'+((index != data.length-1)?'<li class="ui-menu-line"></li>':''));
				}
				listItem.data('itemData', this);
				$sugCtrl.append(listItem);
			});
		}
		
		if(options.appendCallback && $.isFunction(options.appendCallback)){
			$sugCtrl.append(options.appendCallback(data));
		}
	}
	
	
	//构建并显示结果集
	function buildQueryResult(resultData, $keywordCtrl, $sugCtrl, options){
		//根据数据构建结果集
		options.buildSugCallback ? options.buildSugCallback(resultData, $sugCtrl, options) : buildSug(resultData, $keywordCtrl, $sugCtrl, options);
		
		if($sugCtrl.children().length == 0){		//div没有子元素， 则不显示弹层
			return ;
		}
		
		//fnShowLayer($keywordCtrl, $sugCtrl, 'right');//显示自动完成提示层
		$sugCtrl.css({"display":"block"});
		//$("#")
		$("#suggest-cont").css({"display":"block"});
	}
	
	//隐藏自动完成弹层
	function hideSugDiv(layer, options){
		$('#suggest-cont').css({"display":"none"});//改版后需要手动隐藏整个层
		layer.css({"display":"none"});
	}
	
	//请求数据:数据可来自本地或请求服务器(可供外部调用)
	function postRequest($keywordCtrl, $sugCtrl, options){
		//处理响应
		var processResponse = function(responseData){
			hideSugDiv($sugCtrl, options);
			$sugCtrl.empty();
			if( responseData && responseData.length>=0 ){			//返回数据为最终结果
				buildQueryResult(responseData, $keywordCtrl, $sugCtrl, options);
			}else if( responseData && responseData[options.dataKey] && responseData[options.dataKey].length>=0){		//返回数据放在json对象中，存放的默认key为data
				buildQueryResult(responseData[options.dataKey], $keywordCtrl, $sugCtrl, options);
			}
		};
		
		//组装请求参数
		var requestParams = {};
		if( options.setRequestParams && typeof options.setRequestParams === 'function'){		//设置动态请求参数
			options.setRequestParams(requestParams, $keywordCtrl);
		}
		if( requestParams==null || $.isEmptyObject(requestParams) ) return;

		options.pageType = requestParams.pageType;//把请求的房子类型放到参数中；
		if(options.crossDomain){
			$.ajax({
				type: 'GET',
				url: options.urlOrData,
				data: requestParams,
				jsonp: 'jsoncallback',
                dataType: 'jsonp',
                success: processResponse
			});
		}else{
			$.getJSON(options.urlOrData, requestParams, processResponse);
		}
	}
	
	$.fn.extend({
		suggestion:function(options){
			return this.each(function(){
				var index = -1, self = $(this);
				var resultOptions = {};
				$.extend(resultOptions, $.fn.suggestion.defaultOptions, options || {});
				var layer = $('#'+$(this).attr('popdiv'));
				$(document.body).click(function(){
					hideSugDiv(layer, resultOptions);
				});
				var timer;
				var input_box_self = $(this);
				$(this).bind('keyup', function(event){
					if(input_box_self.val() == ""){
						input_box_self.trigger("focus");
						return false;
					}
					var last = layer.find("li.ui-menu-item").length-1;
					switch(event.keyCode){
						case 13: //回车
								if( index != -1 ){
									if( resultOptions.selectCallback ){
										resultOptions.selectCallback(layer.find("li.ui-menu-item").eq(index).data('itemData'), $(this));
									}
								}
								hideSugDiv(layer, resultOptions);
								break;
						case 38: //上箭头
								 if($("#suggest-cont").is(':visible') === false) return;
								 layer.find("li.ui-menu-item").eq(index).removeClass("ui-state-focus");//当前项去掉高亮
								 if(index==0){//如果无前一项则最后一项加上高亮
									 layer.find("li.ui-menu-item:last-child").addClass("ui-state-focus");
									 index = last;
								 }
								 else{//如果有前一项加前一项上高亮
									 layer.find("li.ui-menu-item").eq(index-1).addClass("ui-state-focus");
									 index -= 1;
								 }
								 $(this).val(layer.find("li.ui-menu-item").eq(index).find("i").text());
								 break;
						case 40://下箭头
								if($("#suggest-cont").is(':visible') === false) return;
								 layer.find("li.ui-menu-item").eq(index).removeClass("ui-state-focus");//当前项去掉高亮
								 if(index==-1 || index==last){//如果无后一项则第一项加上高亮
									 layer.find("li.ui-menu-item:first-child").addClass("ui-state-focus");
									 index = 0;
								 }
								 else{//如果有后一项加后一项加上高亮
									 layer.find("li.ui-menu-item").eq(index+1).addClass("ui-state-focus");
									 index += 1;
								 }
								 $(this).val(layer.find("li.ui-menu-item").eq(index).find("i").text());
								 break;
						default:
								var self = $(this);
								if($.trim(self.val()) == ''){
									$("#hot-sug").css({display:'block'});
									$("#suggest-cont").css({display:'none'});
								}
								else{
									$("#hot-sug").css({display:'none'});

									timer = window.setTimeout(function(){
										index = -1;
										postRequest(self, layer, resultOptions);
									}, resultOptions.lazyTime);
								}
								break;
					}
				}).click(function(e){
					//hideSugDiv($("#suggest-cont"), resultOptions);
					//只有不为空的时候 才进行发请求
					if( resultOptions.sugOnfocus ){
						index = -1;
						postRequest($(this), layer, resultOptions);
					}
					stopBubble(e);
				}).keydown(function(e){
					clearTimeout(timer);
					stopBubble(e);
				});
				
				//给自动提示层绑定mouseover事件， 高亮显示选中项
				layer.on("mouseover", 'li', function(){
					$(this).siblings().removeClass('ui-state-focus').end().addClass('ui-state-focus');
					index = layer.find("li").index($(this));
					return false;
				});
			
				//给自动提示层绑定click事件，选择选项
				layer.on('click', 'li', function(){
					self.val($(this).find("i").text());
					self.change();
					self.removeClass('grey999');
					if( resultOptions.selectCallback ){		//点击选项， 用户可自定义回调函数
						resultOptions.selectCallback($(this).data('itemData'), $(this));
					}
					hideSugDiv(layer, resultOptions);
					return false;
				});
			});
		}
	});
	
	//默认配置项
	$.fn.suggestion.defaultOptions = {
		itemTextKey: 'itemText',			//自动完成列表的值， 取自响应数据的itemTextKey指定的值
		sugOnfocus: false,			//获得焦点时， 显示自动完成
		dataKey: 'data',
		hasMask: true,
		lazyTime: 100,
		crossDomain: true
	};
})(jQuery);