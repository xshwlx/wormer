define(['jquery'], function ($) {
    (function (coord) {
        $.extend(coord, {
            menu: {}
        });
//		var roleType = coord.context.getUser('type');
//		console.log(roleType);
//		// if(roleType != 'admin'){
//		// 	roleType = 'user';
//		// }
//		switch(roleType) {
//			case '0':
//				roleType = 'user';
//		        break;
//			case '1':
//		        roleType = 'admin';
//		        break;
//		    case '2':
//		    	roleType = 'domainadmin';
//		    	break;
//		}
        var roleFile = 'js/role/' + 'admin';
        require([roleFile], function (menu) {
            menu.getMenu(coord);
            var len = coord.menu.length;
            for (var i = 0; i < len; i++) {
                var str = coord.menu[i].module;
                coord.menu[i].name = coord.lang.menu[str];
                if (coord.menu[i].nodes.length) {
                    var itemArray = coord.menu[i].nodes;
                    var itemArrayLen = itemArray.length;
                    for (var j = 0; j < itemArrayLen; j++) {
                        var temp = itemArray[j].module;
                        itemArray[j].name = coord.lang.menu[temp];
                    }
                }
            }
        });
    })(window.coord ? window.coord : window.coord = {});
    return window.coord;
});