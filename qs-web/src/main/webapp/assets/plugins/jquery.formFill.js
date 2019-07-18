/**

 * jQuery Form Fill 
 * --------------------------------------------------------------------------
 
 */
(function($){
	
	function Fill() {
		this.defaults = {
			styleElementName: 'none',	// object | none
			dateFormat: 'mm/dd/yy',
			debug: false,
			elementsExecuteEvents: ['checkbox', 'radio', 'select-one']
		};
	};
	
	$.extend(Fill.prototype, {
		setDefaults : function (settings) {
			this.defaults = $.extend({}, this.defaults, settings);
			return this;
		},
		
		fill : function (obj, _element, settings) {
			options = $.extend({}, this.defaults, settings);

			_element.find("*").each(function(i, item){
				
			//	debug( $(item).is("span") );
				
				if ($(item).is("input") || $(item).is("select") || $(item).is("span") || $(item).is("textarea")) {
					try {
						var objName;
						var arrayAtribute;
						try {

							if (options.styleElementName == "object") {
								// Verificando se ?um array
								if ($(item).attr("id").match(/\[[0-9]*\]/i)) {
									objName = $(item).attr("id").replace(/^[a-z]*[0-9]*[a-z]*\./i, 'obj.').replace(/\[[0-9]*\].*/i, "");
									
									arrayAtribute = $(item).attr("id").match(/\[[0-9]*\]\.[a-z0-9]*/i) + "";
									arrayAtribute = arrayAtribute.replace(/\[[0-9]*\]\./i, "");
								} else {
									objName = $(item).attr("id").replace(/^[a-z]*[0-9]*[a-z]*\./i, 'obj.');
								}
							} else if (options.styleElementName == "none") {
								objName = 'obj.' + $(item).attr("id");
							}

                            
							var value = eval(objName);



						} catch(e) {
							if (options.debug) {
								debug(e.message);
							}
						}					

						if (value != null) {
							if (  $(item).is("span") )
							{
							   $(item).html(value);
							}
							//2013.12.24 Harry Tian 添加
							else if( $(item).is("select")){
//								if (value) 
								{
									
									if(value==true){
										value=1;
									}
									else if(value==false){
										value=0;
									}
									
									$(item).val(value);
									$(item).trigger("change");
								}
							}
							else if( $(item).is("textarea")){
//								if (value) 
								{									
									$(item).val(value);									
								}
							}
							//代码添加结束
							else
							 {
							switch ($(item).attr("type")) {
								case "hidden":
								case "password":
								case "textarea":
									$(item).val(value);
								break;

								case "text":
									if ($(item).hasClass("hasDatepicker")) {
										var re = /^[-+]*[0-9]*$/;
										var dateValue = null;
										if (re.test(value)) {
											dateValue = new Date(parseInt(value));
											var strDate = dateValue.getUTCFullYear() + '-' + (dateValue.getUTCMonth() + 1) + '-' + dateValue.getUTCDate();
											dateValue = $.datepicker.parseDate('yy-mm-dd', strDate);
										} else if (value) {										
											dateValue = $.datepicker.parseDate(options.dateFormat, value);
										}
										$(item).datepicker('setDate', dateValue);							
									} else if ($(item).attr("alt") == "double") {
										$(item).val(value.toFixed(2));
									} else {
										$(item).val(value);
									}
								break;

								case "select-one":
									if (value) {
										$(item).val(value);
									}
								break;
								case "radio":
									$(item).each(function (i, radio) {
										if ($(radio).val() == value) {
											$(radio).attr("checked", "checked");
											
											$(item).parent('span').addClass('checked');
										}
									});
								break;
								case "checkbox":
									if ($.isArray(value)) {
										$.each(value, function(i, arrayItem) {
											if (typeof(arrayItem) == 'object') {											
												arrayItemValue = eval("arrayItem." + arrayAtribute);
											} else {
												arrayItemValue = arrayItem;
											}
											if ($(item).val() == arrayItemValue) {
												$(item).attr("checked", "checked");												
												//bootrap 用法
												$(item).parent('span').addClass('checked');
											}
										}); 
									} else {
										if ($(item).val() == value) {
											$(item).attr("checked", "checked");
											
											//bootrap 用法
											$(item).parent('span').addClass('checked');
										}
									}						
								break;
							}
							 }
							executeEvents(item);
						}
					} catch(e) {
						if (options.debug) {
							debug(e.message);
						}
					}

				}

			});
		}
	});
	
	$.fn.fill = function (obj, settings) {
		$.fill.fill(obj, $(this), settings);
		return this;
	};
	
	$.fill = new Fill();
	
	function executeEvents(element) {
		if (jQuery.inArray($(element).attr('type'), $.fill.defaults.elementsExecuteEvents)) {
			if ($(element).attr('onchange')) {
				$(element).change();
			}

			if ($(element).attr('onclick')) {
				$(element).click();
			}
		}	
	};
	
	function debug(message) {                                                                                            // Throws error messages in the browser console.
        if (window.console && window.console.log) {
            window.console.log(message);
        }
    };
})(jQuery);