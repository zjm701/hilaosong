//验证是否为整数或者小数
function checkIntegerOrFloat(text){
	if(null == text || "" == text){
		return false;
	}
    //浮点型 正则
	var folatP = /^(-?\d+)(\.\d+)?$/;
	//整型 正则
	var intP = /^[0-9]*$/;
	
	if(folatP.test(text) || intP.test(text)){
		return true;
	}
	
	return false;
}

//检验为数字
function checkNumber(text){
	if(null == text || "" == text){
		return false;
	}
	//整型 正则
	var intP = /^[0-9]*$/;
	if(intP.test(text)){
		return true;
	}
	return false;
}