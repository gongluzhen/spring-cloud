/**
 * 中国身份证校验
 * 
 * @param code
 *            身份证号
 * @returns {Boolean}
 */
function IdentityCodeValid(code) {
	code = code.replace(/\s/g,"");
	var len = code.length;
	if (len == 8) {
		// 因公普通的是:P+7位数 ,公务的是：S+7位数
		if(/([P|p|S|s]\d{7})$/.test(code)){
			return true;
		}
		// 澳门身份证或不带（）的香港居民身份证
		return IdentityAMCodeValid(code)||IdentityHKCodeValid(code);
	}else if(len == 9){
		// 因私普通护照号码格式有:14/15+7位数,G+8位数.公务的是： S+8位数，电子芯片的普通护照：E+8位数
		if(/^1[45][0-9]{7}|([S|s|G|g]\d{8})|([E|e]\d{8})$/.test(code)){
			return true;
		}
		return false;
	} else if (len == 10) {
		if(/([Gg|Tt|Ss|Ll|Qq|Dd|Aa|Ff]\d{8})$/.test(code)){
			return true;
		}
		// 香港或台湾身份证
		if (code.indexOf("(") > 0||code.indexOf("（") >0) {
			// 香港身份证
			return IdentityHKCodeValid(code);
		} else {
			// 台湾身份证
			return IdentityTWCodeValid(code);
		}
	} else if (len == 11){
		//  H:香港特区护照和香港公民所持回乡卡H开头,后接10位数字;M:澳门特区护照和澳门公民所持回乡卡M开头,后接10位数字
		if(/([H|h|M|m]\d{8,10})$/.test(code)){
			return true;
		}
		return false;
	}else if (len == 18) {// 大陆身份证
		return IdentityCNCodeValid(code);
	}else {// 一定不正确
		return false;
	}
}

/**
 * 中国大陆居民身份证校验 支持15位和18位身份证号 支持地址编码、出生日期、校验位验证
 * 
 * @param code
 * @returns {Boolean}
 */
function IdentityCNCodeValid(code) {
	code = code.toUpperCase();
	var city = {
		11 : "北京",
		12 : "天津",
		13 : "河北",
		14 : "山西",
		15 : "内蒙古",
		21 : "辽宁",
		22 : "吉林",
		23 : "黑龙江 ",
		31 : "上海",
		32 : "江苏",
		33 : "浙江",
		34 : "安徽",
		35 : "福建",
		36 : "江西",
		37 : "山东",
		41 : "河南",
		42 : "湖北 ",
		43 : "湖南",
		44 : "广东",
		45 : "广西",
		46 : "海南",
		50 : "重庆",
		51 : "四川",
		52 : "贵州",
		53 : "云南",
		54 : "西藏 ",
		61 : "陕西",
		62 : "甘肃",
		63 : "青海",
		64 : "宁夏",
		65 : "新疆",
		71 : "台湾",
		81 : "香港",
		82 : "澳门",
		91 : "国外 "
	};
	var pass = true;

	if (!code
			|| !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
					.test(code)) {
		pass = false;
	} else if (!city[code.substr(0, 2)]) {
		pass = false;
	} else {
		// 18位身份证需要验证最后一位校验位
		if (code.length == 18) {
			code = code.split('');
			// ∑(ai×Wi)(mod 11)
			// 加权因子
			var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
			// 校验位
			var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for (var i = 0; i < 17; i++) {
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if (parity[sum % 11] != code[17]) {
				pass = false;
			}
		}
	}
	return pass;
}

/**
 * 中國香港身份證校驗
 * 
 * 一共10位，其中两位是（）
 * 香港身份证号码由三部分组成：一个英文字母；6个数字；括号及0-9中的任一个数字，或者字母A。括号中的数字或字母A，是校验码，用于检验括号前面的号码的逻辑正确性。
 * 先把首位字母改为数字，即A为1，B为2，C为3...Z为26，再乘以8； 然后把字母后面的6个数字依次乘以7、6、5、4、3、2；
 * 再将以上所有乘积相加的和，除以11，得到余数；
 * 如果整除，则括号中的校验码为0，如果余数为1，则校验码为A，如果余数为2～10，则用11减去这个余数的差作校验码。
 * 
 * @param code
 *            身份證號
 * @returns {Boolean}
 */
function IdentityHKCodeValid(str) {
	var strValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	if (str.length < 8)
		return false;
	str=str.replace("（","(");str=str.replace("）",")");
	if (str.charAt(str.length - 3) == '(' && str.charAt(str.length - 1) == ')')
		str = str.substring(0, str.length - 3) + str.charAt(str.length - 2);
	str = str.toUpperCase();
	var hkidPat = /^([A-Z]{1,2})([0-9]{6})([A0-9])$/;
	var matchArray = str.match(hkidPat);
	if (matchArray == null)
		return false;
	var charPart = matchArray[1];
	var numPart = matchArray[2];
	var checkDigit = matchArray[3];
	var checkSum = 0;
	if (charPart.length == 2) {
		checkSum += 9 * (10 + strValidChars.indexOf(charPart.charAt(0)));
		checkSum += 8 * (10 + strValidChars.indexOf(charPart.charAt(1)));
	} else {
		checkSum += 9 * 36;
		checkSum += 8 * (10 + strValidChars.indexOf(charPart));
	}
	for (var i = 0, j = 7; i < numPart.length; i++, j--)
		checkSum += j * numPart.charAt(i);
	var remaining = checkSum % 11;
	var verify = remaining == 0 ? 0 : 11 - remaining;
	return verify == checkDigit || (verify == 10 && checkDigit == 'A');
}

/**
 * 中国澳门身份证校验
 * 
 * 一共8位 澳门身份证号码由8个拉丁数字组成 第一位X，可能是1、5、7。绝大多数人以1字开首
 * 最后一位Y，是查核用数字，是为方便电脑处理资料及检查号码输入的正确性而设。 中间6位数字，是发证当局给出的顺序号。
 * 
 * @param code
 *            身份证号
 * @returns {Boolean}
 */
function IdentityAMCodeValid(code) {
	if (!code || code.length != 8 || !/^[1|5|7][0-9]{6}\([0-9Aa]\)/.test(code)) {
		return false;
	}
	return true;
}

/**
 * 中国台湾身份证校验
 * 
 * 一共10位 最后一位数字是检验码，通过公式计算得到。计算公式如下： 通算值= 首字母对应的第一位验证码+ 首字母对应的第二位验证码 * 9 + 性别码 *
 * 8 + 第二位数字 * 7 + 第三位数字 * 6 + 第四位数字 * 5 + 第五位数字 * 4 + 第六位数字 * 3 + 第七位数字 * 2 +
 * 第八位数字 * 1 最后一位数 =10- 通算值的末尾数。
 * 
 * @param code
 *            身份证号
 * @returns {Boolean}
 */
function IdentityTWCodeValid(idcard) {
	if(/^[A-Z][1-2]\d{8}$/.test(idcard)){
        var area={'A':10,'B':11,'C':12,'D':13,'E':14,'F':15,'G':16,'H':17,'J':18,'K':19,'L':20,'M':21,'N':22,'P':23,'Q':24,'R':25,'S':26,'T':27,'U':28,'V':29,'X':30,'Y':31,'W':32,'Z':33,'I':34,'O':35};
        var idcard_array = new Array();
        idcard_array = idcard.split("");
        var jym=parseInt(area[idcard_array[0]]/10)+9*(area[idcard_array[0]]%10)+8*idcard_array[1]+7*idcard_array[2]+6*idcard_array[3]+5*idcard_array[4]+4*idcard_array[5]+3*idcard_array[6]+2*idcard_array[7]+1*idcard_array[8];
        jym=(10-jym%10)%10;
        if(idcard_array[9]==jym) return true;
    }
    return false;
}