//js表格 生成表格代码
//arrTh 表头信息
//arrTr 数据
var getTable = function(arrTh, arrTr){
    var s = '<table class="tbData">';
    s += '<tr>';
    for(var i=0; i<arrTh.length; i++) {
        s += '<th>' + arrTh[i] + '</th>';
    }
    s += '</tr>';
    for(var i=0; i<arrTr.length; i++) {
        s += '<tr>';
        for(var j=0; j<arrTr[i].length; j++) {
            s += '<td>' + arrTr[i][j] + '</td>';
        }
        s += '</tr>';
    }
    s += '</table>';
    return s;
}

//js分页
//el:分页容器 count:总记录数 pageStep:每页显示多少个 pageNum:第几页 fnGo:分页跳转函数
var jsPage = function(el, count, pageStep, pageNum, fnGo) {
  this.getLink = function(fnGo, index, pageNum, text) {
      var s = '<a href="#p' + index + '" onclick="' + fnGo + '(' + index + ');" ';
      if(index == pageNum) {
          s += 'class="aCur" ';
      }
      text = text || index;
      s += '>' + text + '</a> ';            
      return s;
  }
  
  //总页数
  var pageNumAll = Math.ceil(count / pageStep);
  if (pageNumAll == 1) {
      divPage.innerHTML = '';
      return;
  }
  var itemNum = 5; //当前页左右两边显示个数
  pageNum = Math.max(pageNum, 1);
  pageNum = Math.min(pageNum, pageNumAll);
  var s = '';
  if (pageNum > 1) {
      s += this.getLink(fnGo, pageNum-1, pageNum, '上一页');
  } else {
      s += '<span>上一页</span> ';
  }
  var begin = 1;
  if (pageNum - itemNum > 1) {
      s += this.getLink(fnGo, 1, pageNum) + '... ';
      begin = pageNum - itemNum;
  }
  var end = Math.min(pageNumAll, begin + itemNum*2);
  if(end == pageNumAll - 1){
      end = pageNumAll;
  }
  for (var i = begin; i <= end; i++) {
      s += this.getLink(fnGo, i, pageNum);
  }
  if (end < pageNumAll) {
      s += '... ' + this.getLink(fnGo, pageNumAll, pageNum);
  }
  if (pageNum < pageNumAll) {
      s += this.getLink(fnGo, pageNum+1, pageNum, '下一页');
  } else {
      s += '<span>下一页</span> ';
  }
  var divPage = document.getElementById(el);
  divPage.innerHTML = s;
}

//js随机内容
var jsRand = {};
//随机数字
jsRand.int = function(min,max){
    return this.show('i', min, max);
}
//随机字符
jsRand.str = jsRand.string = function(min,max){
    return this.show('', min, max);
}
//随机日期
jsRand.date = function(){
    return this.show('d');
}
//随机金额
jsRand.money = function(min,max){
    return this.show('m', min, max);
}
//随机汉字
jsRand.ch = function(min,max){
    return this.show('ch', min, max);
}
jsRand.show = function(type,min,max) {
    var str;
    if(type == 'i') {
        str = this.rand(min, max);
    } else if(type == 'm') {
        str = '￥' + this.rand(min, max) + '.00';
    } else if(type == 'd') {
        str = this.rand(1990,2020) + '-';
        str += this.rand(1,12,2) + '-';
        str += this.rand(1,31,2) + ' ';
        str += this.rand(0,23,2) + ':';
        str += this.rand(1,59,2);
    } else {
        min = min||0;
        max = max||10;
        str = '';
        var len = this.rand(min, max);
        for(var i=0; i<len; i++) {
            var iChar = this.rand(48, 122);
            if(type == 'ch') {
                iChar = this.rand(19968, 40869);
            }
            var chr = String.fromCharCode(iChar);
            chr = chr.replace(/&/g,"&amp;")
                    .replace(/</g,"&lt;")
                    .replace(/>/g,"&gt;")
                    .replace(/ /g,"&nbsp;")
                    .replace(/'/g,"&apos;")
                    .replace(/"/g,"&quot;");
            str += chr;
        }
    }
    
    return str;
}

jsRand.rand = function(min, max, len) {
    min = min||0;
    max = max||1000;
    var val = min + Math.round(Math.random() * (max-min));
    if(len) {
        while((''+val).length < len) {
            val = '0' + val;
        }
    }
    return val;
}