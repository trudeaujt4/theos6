/**
 * Created by zihong on 2015/9/14.
 */
define([
    'zepto',
    'template',
    'clientApi/clientUI',
    'clientApi/clientInfo',
    'api/helper/util',
    'api/nativeUI/widget',
    'clientApi/testApi',
    'domReady!'
],

function ($,template,clientUI,clientInfo,util,widget,testApi) {
    function TestDo(){
    }

    TestDo.prototype.render=function(){
        $('body').on('click','.toast',function(){
            widget.toast('this is a test');
        });

        $('body').on('click','.alert',function(){
            widget.alertDialog('this is a test','this is a test message',function(){ alert("callback normal"); },'OK');
        });

        // alertClientUI
        $('body').on('click','.alertClientUI',function(){
            clientUI.clientToast('this is a test','warn');
        });

        $('body').on('click','.testInfo',function(){
            alert(1);
            testApi.getTestInfo(function(data){
                console.log("callback:"+data);
            },"test getTestInfo fun");
        });
    };


    TestDo.prototype.init=function(){
        var t=this;
        testApi.start(function () {
            t.render();
        });
    };

    return new TestDo();
});