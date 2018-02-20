/******/ (function(modules) { // webpackBootstrap
/******/ 	// install a JSONP callback for chunk loading
/******/ 	var parentJsonpFunction = window["webpackJsonp"];
/******/ 	window["webpackJsonp"] = function webpackJsonpCallback(chunkIds, moreModules) {
/******/ 		// add "moreModules" to the modules object,
/******/ 		// then flag all "chunkIds" as loaded and fire callback
/******/ 		var moduleId, chunkId, i = 0, callbacks = [];
/******/ 		for(;i < chunkIds.length; i++) {
/******/ 			chunkId = chunkIds[i];
/******/ 			if(installedChunks[chunkId])
/******/ 				callbacks.push.apply(callbacks, installedChunks[chunkId]);
/******/ 			installedChunks[chunkId] = 0;
/******/ 		}
/******/ 		for(moduleId in moreModules) {
/******/ 			modules[moduleId] = moreModules[moduleId];
/******/ 		}
/******/ 		if(parentJsonpFunction) parentJsonpFunction(chunkIds, moreModules);
/******/ 		while(callbacks.length)
/******/ 			callbacks.shift().call(null, __webpack_require__);

/******/ 	};

/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// object to store loaded and loading chunks
/******/ 	// "0" means "already loaded"
/******/ 	// Array means "loading", array contains callbacks
/******/ 	var installedChunks = {
/******/ 		11:0
/******/ 	};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}

/******/ 	// This file contains only the entry chunk.
/******/ 	// The chunk loading function for additional chunks
/******/ 	__webpack_require__.e = function requireEnsure(chunkId, callback) {
/******/ 		// "0" is the signal for "already loaded"
/******/ 		if(installedChunks[chunkId] === 0)
/******/ 			return callback.call(null, __webpack_require__);

/******/ 		// an array means "currently loading".
/******/ 		if(installedChunks[chunkId] !== undefined) {
/******/ 			installedChunks[chunkId].push(callback);
/******/ 		} else {
/******/ 			// start chunk loading
/******/ 			installedChunks[chunkId] = [callback];
/******/ 			var head = document.getElementsByTagName('head')[0];
/******/ 			var script = document.createElement('script');
/******/ 			script.type = 'text/javascript';
/******/ 			script.charset = 'utf-8';
/******/ 			script.async = true;

/******/ 			script.src = __webpack_require__.p + "" + chunkId + "." + ({}[chunkId]||chunkId) + "." + {"1":"d1dfba684a37d885c30a","2":"d85c5f2541fd3e0163f0","3":"56ce794daa75edd15bbe"}[chunkId] + ".js";
/******/ 			head.appendChild(script);
/******/ 		}
/******/ 	};

/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "http://res.maka.im/cdn/store2/release/";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _extends2 = __webpack_require__(1);

	var _extends3 = _interopRequireDefault(_extends2);

	var _promise = __webpack_require__(39);

	var _promise2 = _interopRequireDefault(_promise);

	var _vue = __webpack_require__(73);

	var _vue2 = _interopRequireDefault(_vue);

	var _templatePay = __webpack_require__(259);

	var _templatePay2 = _interopRequireDefault(_templatePay);

	var _newLogin = __webpack_require__(74);

	var _newLogin2 = _interopRequireDefault(_newLogin);

	__webpack_require__(281);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	new _promise2.default(function (resolve) {
	  if (window.addEventListener) {
	    window.addEventListener('DOMContentLoaded', resolve);
	  } else {
	    window.attachEvent('onload', resolve);
	  }
	}).then(function (event) {
	  window.payComponent = new _vue2.default((0, _extends3.default)({ el: '#template-pay' }, _templatePay2.default));
	  window.loginCompontent = new _vue2.default((0, _extends3.default)({ el: '#login-compontent' }, _newLogin2.default));
	});

/***/ },
/* 1 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	exports.__esModule = true;

	var _assign = __webpack_require__(2);

	var _assign2 = _interopRequireDefault(_assign);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = _assign2.default || function (target) {
	  for (var i = 1; i < arguments.length; i++) {
	    var source = arguments[i];

	    for (var key in source) {
	      if (Object.prototype.hasOwnProperty.call(source, key)) {
	        target[key] = source[key];
	      }
	    }
	  }

	  return target;
	};

/***/ },
/* 2 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(3), __esModule: true };

/***/ },
/* 3 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(4);
	module.exports = __webpack_require__(7).Object.assign;

/***/ },
/* 4 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.3.1 Object.assign(target, source)
	var $export = __webpack_require__(5);

	$export($export.S + $export.F, 'Object', {assign: __webpack_require__(20)});

/***/ },
/* 5 */
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(6)
	  , core      = __webpack_require__(7)
	  , ctx       = __webpack_require__(8)
	  , hide      = __webpack_require__(10)
	  , PROTOTYPE = 'prototype';

	var $export = function(type, name, source){
	  var IS_FORCED = type & $export.F
	    , IS_GLOBAL = type & $export.G
	    , IS_STATIC = type & $export.S
	    , IS_PROTO  = type & $export.P
	    , IS_BIND   = type & $export.B
	    , IS_WRAP   = type & $export.W
	    , exports   = IS_GLOBAL ? core : core[name] || (core[name] = {})
	    , expProto  = exports[PROTOTYPE]
	    , target    = IS_GLOBAL ? global : IS_STATIC ? global[name] : (global[name] || {})[PROTOTYPE]
	    , key, own, out;
	  if(IS_GLOBAL)source = name;
	  for(key in source){
	    // contains in native
	    own = !IS_FORCED && target && target[key] !== undefined;
	    if(own && key in exports)continue;
	    // export native or passed
	    out = own ? target[key] : source[key];
	    // prevent global pollution for namespaces
	    exports[key] = IS_GLOBAL && typeof target[key] != 'function' ? source[key]
	    // bind timers to global for call from export context
	    : IS_BIND && own ? ctx(out, global)
	    // wrap global constructors for prevent change them in library
	    : IS_WRAP && target[key] == out ? (function(C){
	      var F = function(a, b, c){
	        if(this instanceof C){
	          switch(arguments.length){
	            case 0: return new C;
	            case 1: return new C(a);
	            case 2: return new C(a, b);
	          } return new C(a, b, c);
	        } return C.apply(this, arguments);
	      };
	      F[PROTOTYPE] = C[PROTOTYPE];
	      return F;
	    // make static versions for prototype methods
	    })(out) : IS_PROTO && typeof out == 'function' ? ctx(Function.call, out) : out;
	    // export proto methods to core.%CONSTRUCTOR%.methods.%NAME%
	    if(IS_PROTO){
	      (exports.virtual || (exports.virtual = {}))[key] = out;
	      // export proto methods to core.%CONSTRUCTOR%.prototype.%NAME%
	      if(type & $export.R && expProto && !expProto[key])hide(expProto, key, out);
	    }
	  }
	};
	// type bitmap
	$export.F = 1;   // forced
	$export.G = 2;   // global
	$export.S = 4;   // static
	$export.P = 8;   // proto
	$export.B = 16;  // bind
	$export.W = 32;  // wrap
	$export.U = 64;  // safe
	$export.R = 128; // real proto method for `library` 
	module.exports = $export;

/***/ },
/* 6 */
/***/ function(module, exports) {

	// https://github.com/zloirock/core-js/issues/86#issuecomment-115759028
	var global = module.exports = typeof window != 'undefined' && window.Math == Math
	  ? window : typeof self != 'undefined' && self.Math == Math ? self : Function('return this')();
	if(typeof __g == 'number')__g = global; // eslint-disable-line no-undef

/***/ },
/* 7 */
/***/ function(module, exports) {

	var core = module.exports = {version: '2.4.0'};
	if(typeof __e == 'number')__e = core; // eslint-disable-line no-undef

/***/ },
/* 8 */
/***/ function(module, exports, __webpack_require__) {

	// optional / simple context binding
	var aFunction = __webpack_require__(9);
	module.exports = function(fn, that, length){
	  aFunction(fn);
	  if(that === undefined)return fn;
	  switch(length){
	    case 1: return function(a){
	      return fn.call(that, a);
	    };
	    case 2: return function(a, b){
	      return fn.call(that, a, b);
	    };
	    case 3: return function(a, b, c){
	      return fn.call(that, a, b, c);
	    };
	  }
	  return function(/* ...args */){
	    return fn.apply(that, arguments);
	  };
	};

/***/ },
/* 9 */
/***/ function(module, exports) {

	module.exports = function(it){
	  if(typeof it != 'function')throw TypeError(it + ' is not a function!');
	  return it;
	};

/***/ },
/* 10 */
/***/ function(module, exports, __webpack_require__) {

	var dP         = __webpack_require__(11)
	  , createDesc = __webpack_require__(19);
	module.exports = __webpack_require__(15) ? function(object, key, value){
	  return dP.f(object, key, createDesc(1, value));
	} : function(object, key, value){
	  object[key] = value;
	  return object;
	};

/***/ },
/* 11 */
/***/ function(module, exports, __webpack_require__) {

	var anObject       = __webpack_require__(12)
	  , IE8_DOM_DEFINE = __webpack_require__(14)
	  , toPrimitive    = __webpack_require__(18)
	  , dP             = Object.defineProperty;

	exports.f = __webpack_require__(15) ? Object.defineProperty : function defineProperty(O, P, Attributes){
	  anObject(O);
	  P = toPrimitive(P, true);
	  anObject(Attributes);
	  if(IE8_DOM_DEFINE)try {
	    return dP(O, P, Attributes);
	  } catch(e){ /* empty */ }
	  if('get' in Attributes || 'set' in Attributes)throw TypeError('Accessors not supported!');
	  if('value' in Attributes)O[P] = Attributes.value;
	  return O;
	};

/***/ },
/* 12 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(13);
	module.exports = function(it){
	  if(!isObject(it))throw TypeError(it + ' is not an object!');
	  return it;
	};

/***/ },
/* 13 */
/***/ function(module, exports) {

	module.exports = function(it){
	  return typeof it === 'object' ? it !== null : typeof it === 'function';
	};

/***/ },
/* 14 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = !__webpack_require__(15) && !__webpack_require__(16)(function(){
	  return Object.defineProperty(__webpack_require__(17)('div'), 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 15 */
/***/ function(module, exports, __webpack_require__) {

	// Thank's IE8 for his funny defineProperty
	module.exports = !__webpack_require__(16)(function(){
	  return Object.defineProperty({}, 'a', {get: function(){ return 7; }}).a != 7;
	});

/***/ },
/* 16 */
/***/ function(module, exports) {

	module.exports = function(exec){
	  try {
	    return !!exec();
	  } catch(e){
	    return true;
	  }
	};

/***/ },
/* 17 */
/***/ function(module, exports, __webpack_require__) {

	var isObject = __webpack_require__(13)
	  , document = __webpack_require__(6).document
	  // in old IE typeof document.createElement is 'object'
	  , is = isObject(document) && isObject(document.createElement);
	module.exports = function(it){
	  return is ? document.createElement(it) : {};
	};

/***/ },
/* 18 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.1 ToPrimitive(input [, PreferredType])
	var isObject = __webpack_require__(13);
	// instead of the ES6 spec version, we didn't implement @@toPrimitive case
	// and the second argument - flag - preferred type is a string
	module.exports = function(it, S){
	  if(!isObject(it))return it;
	  var fn, val;
	  if(S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it)))return val;
	  if(typeof (fn = it.valueOf) == 'function' && !isObject(val = fn.call(it)))return val;
	  if(!S && typeof (fn = it.toString) == 'function' && !isObject(val = fn.call(it)))return val;
	  throw TypeError("Can't convert object to primitive value");
	};

/***/ },
/* 19 */
/***/ function(module, exports) {

	module.exports = function(bitmap, value){
	  return {
	    enumerable  : !(bitmap & 1),
	    configurable: !(bitmap & 2),
	    writable    : !(bitmap & 4),
	    value       : value
	  };
	};

/***/ },
/* 20 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// 19.1.2.1 Object.assign(target, source, ...)
	var getKeys  = __webpack_require__(21)
	  , gOPS     = __webpack_require__(36)
	  , pIE      = __webpack_require__(37)
	  , toObject = __webpack_require__(38)
	  , IObject  = __webpack_require__(25)
	  , $assign  = Object.assign;

	// should work with symbols and should have deterministic property order (V8 bug)
	module.exports = !$assign || __webpack_require__(16)(function(){
	  var A = {}
	    , B = {}
	    , S = Symbol()
	    , K = 'abcdefghijklmnopqrst';
	  A[S] = 7;
	  K.split('').forEach(function(k){ B[k] = k; });
	  return $assign({}, A)[S] != 7 || Object.keys($assign({}, B)).join('') != K;
	}) ? function assign(target, source){ // eslint-disable-line no-unused-vars
	  var T     = toObject(target)
	    , aLen  = arguments.length
	    , index = 1
	    , getSymbols = gOPS.f
	    , isEnum     = pIE.f;
	  while(aLen > index){
	    var S      = IObject(arguments[index++])
	      , keys   = getSymbols ? getKeys(S).concat(getSymbols(S)) : getKeys(S)
	      , length = keys.length
	      , j      = 0
	      , key;
	    while(length > j)if(isEnum.call(S, key = keys[j++]))T[key] = S[key];
	  } return T;
	} : $assign;

/***/ },
/* 21 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.14 / 15.2.3.14 Object.keys(O)
	var $keys       = __webpack_require__(22)
	  , enumBugKeys = __webpack_require__(35);

	module.exports = Object.keys || function keys(O){
	  return $keys(O, enumBugKeys);
	};

/***/ },
/* 22 */
/***/ function(module, exports, __webpack_require__) {

	var has          = __webpack_require__(23)
	  , toIObject    = __webpack_require__(24)
	  , arrayIndexOf = __webpack_require__(28)(false)
	  , IE_PROTO     = __webpack_require__(32)('IE_PROTO');

	module.exports = function(object, names){
	  var O      = toIObject(object)
	    , i      = 0
	    , result = []
	    , key;
	  for(key in O)if(key != IE_PROTO)has(O, key) && result.push(key);
	  // Don't enum bug & hidden keys
	  while(names.length > i)if(has(O, key = names[i++])){
	    ~arrayIndexOf(result, key) || result.push(key);
	  }
	  return result;
	};

/***/ },
/* 23 */
/***/ function(module, exports) {

	var hasOwnProperty = {}.hasOwnProperty;
	module.exports = function(it, key){
	  return hasOwnProperty.call(it, key);
	};

/***/ },
/* 24 */
/***/ function(module, exports, __webpack_require__) {

	// to indexed object, toObject with fallback for non-array-like ES3 strings
	var IObject = __webpack_require__(25)
	  , defined = __webpack_require__(27);
	module.exports = function(it){
	  return IObject(defined(it));
	};

/***/ },
/* 25 */
/***/ function(module, exports, __webpack_require__) {

	// fallback for non-array-like ES3 and non-enumerable old V8 strings
	var cof = __webpack_require__(26);
	module.exports = Object('z').propertyIsEnumerable(0) ? Object : function(it){
	  return cof(it) == 'String' ? it.split('') : Object(it);
	};

/***/ },
/* 26 */
/***/ function(module, exports) {

	var toString = {}.toString;

	module.exports = function(it){
	  return toString.call(it).slice(8, -1);
	};

/***/ },
/* 27 */
/***/ function(module, exports) {

	// 7.2.1 RequireObjectCoercible(argument)
	module.exports = function(it){
	  if(it == undefined)throw TypeError("Can't call method on  " + it);
	  return it;
	};

/***/ },
/* 28 */
/***/ function(module, exports, __webpack_require__) {

	// false -> Array#indexOf
	// true  -> Array#includes
	var toIObject = __webpack_require__(24)
	  , toLength  = __webpack_require__(29)
	  , toIndex   = __webpack_require__(31);
	module.exports = function(IS_INCLUDES){
	  return function($this, el, fromIndex){
	    var O      = toIObject($this)
	      , length = toLength(O.length)
	      , index  = toIndex(fromIndex, length)
	      , value;
	    // Array#includes uses SameValueZero equality algorithm
	    if(IS_INCLUDES && el != el)while(length > index){
	      value = O[index++];
	      if(value != value)return true;
	    // Array#toIndex ignores holes, Array#includes - not
	    } else for(;length > index; index++)if(IS_INCLUDES || index in O){
	      if(O[index] === el)return IS_INCLUDES || index || 0;
	    } return !IS_INCLUDES && -1;
	  };
	};

/***/ },
/* 29 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.15 ToLength
	var toInteger = __webpack_require__(30)
	  , min       = Math.min;
	module.exports = function(it){
	  return it > 0 ? min(toInteger(it), 0x1fffffffffffff) : 0; // pow(2, 53) - 1 == 9007199254740991
	};

/***/ },
/* 30 */
/***/ function(module, exports) {

	// 7.1.4 ToInteger
	var ceil  = Math.ceil
	  , floor = Math.floor;
	module.exports = function(it){
	  return isNaN(it = +it) ? 0 : (it > 0 ? floor : ceil)(it);
	};

/***/ },
/* 31 */
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(30)
	  , max       = Math.max
	  , min       = Math.min;
	module.exports = function(index, length){
	  index = toInteger(index);
	  return index < 0 ? max(index + length, 0) : min(index, length);
	};

/***/ },
/* 32 */
/***/ function(module, exports, __webpack_require__) {

	var shared = __webpack_require__(33)('keys')
	  , uid    = __webpack_require__(34);
	module.exports = function(key){
	  return shared[key] || (shared[key] = uid(key));
	};

/***/ },
/* 33 */
/***/ function(module, exports, __webpack_require__) {

	var global = __webpack_require__(6)
	  , SHARED = '__core-js_shared__'
	  , store  = global[SHARED] || (global[SHARED] = {});
	module.exports = function(key){
	  return store[key] || (store[key] = {});
	};

/***/ },
/* 34 */
/***/ function(module, exports) {

	var id = 0
	  , px = Math.random();
	module.exports = function(key){
	  return 'Symbol('.concat(key === undefined ? '' : key, ')_', (++id + px).toString(36));
	};

/***/ },
/* 35 */
/***/ function(module, exports) {

	// IE 8- don't enum bug keys
	module.exports = (
	  'constructor,hasOwnProperty,isPrototypeOf,propertyIsEnumerable,toLocaleString,toString,valueOf'
	).split(',');

/***/ },
/* 36 */
/***/ function(module, exports) {

	exports.f = Object.getOwnPropertySymbols;

/***/ },
/* 37 */
/***/ function(module, exports) {

	exports.f = {}.propertyIsEnumerable;

/***/ },
/* 38 */
/***/ function(module, exports, __webpack_require__) {

	// 7.1.13 ToObject(argument)
	var defined = __webpack_require__(27);
	module.exports = function(it){
	  return Object(defined(it));
	};

/***/ },
/* 39 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(40), __esModule: true };

/***/ },
/* 40 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(41);
	__webpack_require__(42);
	__webpack_require__(55);
	__webpack_require__(59);
	module.exports = __webpack_require__(7).Promise;

/***/ },
/* 41 */
/***/ function(module, exports) {

	

/***/ },
/* 42 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var $at  = __webpack_require__(43)(true);

	// 21.1.3.27 String.prototype[@@iterator]()
	__webpack_require__(44)(String, 'String', function(iterated){
	  this._t = String(iterated); // target
	  this._i = 0;                // next index
	// 21.1.5.2.1 %StringIteratorPrototype%.next()
	}, function(){
	  var O     = this._t
	    , index = this._i
	    , point;
	  if(index >= O.length)return {value: undefined, done: true};
	  point = $at(O, index);
	  this._i += point.length;
	  return {value: point, done: false};
	});

/***/ },
/* 43 */
/***/ function(module, exports, __webpack_require__) {

	var toInteger = __webpack_require__(30)
	  , defined   = __webpack_require__(27);
	// true  -> String#at
	// false -> String#codePointAt
	module.exports = function(TO_STRING){
	  return function(that, pos){
	    var s = String(defined(that))
	      , i = toInteger(pos)
	      , l = s.length
	      , a, b;
	    if(i < 0 || i >= l)return TO_STRING ? '' : undefined;
	    a = s.charCodeAt(i);
	    return a < 0xd800 || a > 0xdbff || i + 1 === l || (b = s.charCodeAt(i + 1)) < 0xdc00 || b > 0xdfff
	      ? TO_STRING ? s.charAt(i) : a
	      : TO_STRING ? s.slice(i, i + 2) : (a - 0xd800 << 10) + (b - 0xdc00) + 0x10000;
	  };
	};

/***/ },
/* 44 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var LIBRARY        = __webpack_require__(45)
	  , $export        = __webpack_require__(5)
	  , redefine       = __webpack_require__(46)
	  , hide           = __webpack_require__(10)
	  , has            = __webpack_require__(23)
	  , Iterators      = __webpack_require__(47)
	  , $iterCreate    = __webpack_require__(48)
	  , setToStringTag = __webpack_require__(52)
	  , getPrototypeOf = __webpack_require__(54)
	  , ITERATOR       = __webpack_require__(53)('iterator')
	  , BUGGY          = !([].keys && 'next' in [].keys()) // Safari has buggy iterators w/o `next`
	  , FF_ITERATOR    = '@@iterator'
	  , KEYS           = 'keys'
	  , VALUES         = 'values';

	var returnThis = function(){ return this; };

	module.exports = function(Base, NAME, Constructor, next, DEFAULT, IS_SET, FORCED){
	  $iterCreate(Constructor, NAME, next);
	  var getMethod = function(kind){
	    if(!BUGGY && kind in proto)return proto[kind];
	    switch(kind){
	      case KEYS: return function keys(){ return new Constructor(this, kind); };
	      case VALUES: return function values(){ return new Constructor(this, kind); };
	    } return function entries(){ return new Constructor(this, kind); };
	  };
	  var TAG        = NAME + ' Iterator'
	    , DEF_VALUES = DEFAULT == VALUES
	    , VALUES_BUG = false
	    , proto      = Base.prototype
	    , $native    = proto[ITERATOR] || proto[FF_ITERATOR] || DEFAULT && proto[DEFAULT]
	    , $default   = $native || getMethod(DEFAULT)
	    , $entries   = DEFAULT ? !DEF_VALUES ? $default : getMethod('entries') : undefined
	    , $anyNative = NAME == 'Array' ? proto.entries || $native : $native
	    , methods, key, IteratorPrototype;
	  // Fix native
	  if($anyNative){
	    IteratorPrototype = getPrototypeOf($anyNative.call(new Base));
	    if(IteratorPrototype !== Object.prototype){
	      // Set @@toStringTag to native iterators
	      setToStringTag(IteratorPrototype, TAG, true);
	      // fix for some old engines
	      if(!LIBRARY && !has(IteratorPrototype, ITERATOR))hide(IteratorPrototype, ITERATOR, returnThis);
	    }
	  }
	  // fix Array#{values, @@iterator}.name in V8 / FF
	  if(DEF_VALUES && $native && $native.name !== VALUES){
	    VALUES_BUG = true;
	    $default = function values(){ return $native.call(this); };
	  }
	  // Define iterator
	  if((!LIBRARY || FORCED) && (BUGGY || VALUES_BUG || !proto[ITERATOR])){
	    hide(proto, ITERATOR, $default);
	  }
	  // Plug for library
	  Iterators[NAME] = $default;
	  Iterators[TAG]  = returnThis;
	  if(DEFAULT){
	    methods = {
	      values:  DEF_VALUES ? $default : getMethod(VALUES),
	      keys:    IS_SET     ? $default : getMethod(KEYS),
	      entries: $entries
	    };
	    if(FORCED)for(key in methods){
	      if(!(key in proto))redefine(proto, key, methods[key]);
	    } else $export($export.P + $export.F * (BUGGY || VALUES_BUG), NAME, methods);
	  }
	  return methods;
	};

/***/ },
/* 45 */
/***/ function(module, exports) {

	module.exports = true;

/***/ },
/* 46 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(10);

/***/ },
/* 47 */
/***/ function(module, exports) {

	module.exports = {};

/***/ },
/* 48 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var create         = __webpack_require__(49)
	  , descriptor     = __webpack_require__(19)
	  , setToStringTag = __webpack_require__(52)
	  , IteratorPrototype = {};

	// 25.1.2.1.1 %IteratorPrototype%[@@iterator]()
	__webpack_require__(10)(IteratorPrototype, __webpack_require__(53)('iterator'), function(){ return this; });

	module.exports = function(Constructor, NAME, next){
	  Constructor.prototype = create(IteratorPrototype, {next: descriptor(1, next)});
	  setToStringTag(Constructor, NAME + ' Iterator');
	};

/***/ },
/* 49 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.2 / 15.2.3.5 Object.create(O [, Properties])
	var anObject    = __webpack_require__(12)
	  , dPs         = __webpack_require__(50)
	  , enumBugKeys = __webpack_require__(35)
	  , IE_PROTO    = __webpack_require__(32)('IE_PROTO')
	  , Empty       = function(){ /* empty */ }
	  , PROTOTYPE   = 'prototype';

	// Create object with fake `null` prototype: use iframe Object with cleared prototype
	var createDict = function(){
	  // Thrash, waste and sodomy: IE GC bug
	  var iframe = __webpack_require__(17)('iframe')
	    , i      = enumBugKeys.length
	    , lt     = '<'
	    , gt     = '>'
	    , iframeDocument;
	  iframe.style.display = 'none';
	  __webpack_require__(51).appendChild(iframe);
	  iframe.src = 'javascript:'; // eslint-disable-line no-script-url
	  // createDict = iframe.contentWindow.Object;
	  // html.removeChild(iframe);
	  iframeDocument = iframe.contentWindow.document;
	  iframeDocument.open();
	  iframeDocument.write(lt + 'script' + gt + 'document.F=Object' + lt + '/script' + gt);
	  iframeDocument.close();
	  createDict = iframeDocument.F;
	  while(i--)delete createDict[PROTOTYPE][enumBugKeys[i]];
	  return createDict();
	};

	module.exports = Object.create || function create(O, Properties){
	  var result;
	  if(O !== null){
	    Empty[PROTOTYPE] = anObject(O);
	    result = new Empty;
	    Empty[PROTOTYPE] = null;
	    // add "__proto__" for Object.getPrototypeOf polyfill
	    result[IE_PROTO] = O;
	  } else result = createDict();
	  return Properties === undefined ? result : dPs(result, Properties);
	};


/***/ },
/* 50 */
/***/ function(module, exports, __webpack_require__) {

	var dP       = __webpack_require__(11)
	  , anObject = __webpack_require__(12)
	  , getKeys  = __webpack_require__(21);

	module.exports = __webpack_require__(15) ? Object.defineProperties : function defineProperties(O, Properties){
	  anObject(O);
	  var keys   = getKeys(Properties)
	    , length = keys.length
	    , i = 0
	    , P;
	  while(length > i)dP.f(O, P = keys[i++], Properties[P]);
	  return O;
	};

/***/ },
/* 51 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(6).document && document.documentElement;

/***/ },
/* 52 */
/***/ function(module, exports, __webpack_require__) {

	var def = __webpack_require__(11).f
	  , has = __webpack_require__(23)
	  , TAG = __webpack_require__(53)('toStringTag');

	module.exports = function(it, tag, stat){
	  if(it && !has(it = stat ? it : it.prototype, TAG))def(it, TAG, {configurable: true, value: tag});
	};

/***/ },
/* 53 */
/***/ function(module, exports, __webpack_require__) {

	var store      = __webpack_require__(33)('wks')
	  , uid        = __webpack_require__(34)
	  , Symbol     = __webpack_require__(6).Symbol
	  , USE_SYMBOL = typeof Symbol == 'function';

	var $exports = module.exports = function(name){
	  return store[name] || (store[name] =
	    USE_SYMBOL && Symbol[name] || (USE_SYMBOL ? Symbol : uid)('Symbol.' + name));
	};

	$exports.store = store;

/***/ },
/* 54 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.9 / 15.2.3.2 Object.getPrototypeOf(O)
	var has         = __webpack_require__(23)
	  , toObject    = __webpack_require__(38)
	  , IE_PROTO    = __webpack_require__(32)('IE_PROTO')
	  , ObjectProto = Object.prototype;

	module.exports = Object.getPrototypeOf || function(O){
	  O = toObject(O);
	  if(has(O, IE_PROTO))return O[IE_PROTO];
	  if(typeof O.constructor == 'function' && O instanceof O.constructor){
	    return O.constructor.prototype;
	  } return O instanceof Object ? ObjectProto : null;
	};

/***/ },
/* 55 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(56);
	var global        = __webpack_require__(6)
	  , hide          = __webpack_require__(10)
	  , Iterators     = __webpack_require__(47)
	  , TO_STRING_TAG = __webpack_require__(53)('toStringTag');

	for(var collections = ['NodeList', 'DOMTokenList', 'MediaList', 'StyleSheetList', 'CSSRuleList'], i = 0; i < 5; i++){
	  var NAME       = collections[i]
	    , Collection = global[NAME]
	    , proto      = Collection && Collection.prototype;
	  if(proto && !proto[TO_STRING_TAG])hide(proto, TO_STRING_TAG, NAME);
	  Iterators[NAME] = Iterators.Array;
	}

/***/ },
/* 56 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var addToUnscopables = __webpack_require__(57)
	  , step             = __webpack_require__(58)
	  , Iterators        = __webpack_require__(47)
	  , toIObject        = __webpack_require__(24);

	// 22.1.3.4 Array.prototype.entries()
	// 22.1.3.13 Array.prototype.keys()
	// 22.1.3.29 Array.prototype.values()
	// 22.1.3.30 Array.prototype[@@iterator]()
	module.exports = __webpack_require__(44)(Array, 'Array', function(iterated, kind){
	  this._t = toIObject(iterated); // target
	  this._i = 0;                   // next index
	  this._k = kind;                // kind
	// 22.1.5.2.1 %ArrayIteratorPrototype%.next()
	}, function(){
	  var O     = this._t
	    , kind  = this._k
	    , index = this._i++;
	  if(!O || index >= O.length){
	    this._t = undefined;
	    return step(1);
	  }
	  if(kind == 'keys'  )return step(0, index);
	  if(kind == 'values')return step(0, O[index]);
	  return step(0, [index, O[index]]);
	}, 'values');

	// argumentsList[@@iterator] is %ArrayProto_values% (9.4.4.6, 9.4.4.7)
	Iterators.Arguments = Iterators.Array;

	addToUnscopables('keys');
	addToUnscopables('values');
	addToUnscopables('entries');

/***/ },
/* 57 */
/***/ function(module, exports) {

	module.exports = function(){ /* empty */ };

/***/ },
/* 58 */
/***/ function(module, exports) {

	module.exports = function(done, value){
	  return {value: value, done: !!done};
	};

/***/ },
/* 59 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var LIBRARY            = __webpack_require__(45)
	  , global             = __webpack_require__(6)
	  , ctx                = __webpack_require__(8)
	  , classof            = __webpack_require__(60)
	  , $export            = __webpack_require__(5)
	  , isObject           = __webpack_require__(13)
	  , aFunction          = __webpack_require__(9)
	  , anInstance         = __webpack_require__(61)
	  , forOf              = __webpack_require__(62)
	  , speciesConstructor = __webpack_require__(66)
	  , task               = __webpack_require__(67).set
	  , microtask          = __webpack_require__(69)()
	  , PROMISE            = 'Promise'
	  , TypeError          = global.TypeError
	  , process            = global.process
	  , $Promise           = global[PROMISE]
	  , process            = global.process
	  , isNode             = classof(process) == 'process'
	  , empty              = function(){ /* empty */ }
	  , Internal, GenericPromiseCapability, Wrapper;

	var USE_NATIVE = !!function(){
	  try {
	    // correct subclassing with @@species support
	    var promise     = $Promise.resolve(1)
	      , FakePromise = (promise.constructor = {})[__webpack_require__(53)('species')] = function(exec){ exec(empty, empty); };
	    // unhandled rejections tracking support, NodeJS Promise without it fails @@species test
	    return (isNode || typeof PromiseRejectionEvent == 'function') && promise.then(empty) instanceof FakePromise;
	  } catch(e){ /* empty */ }
	}();

	// helpers
	var sameConstructor = function(a, b){
	  // with library wrapper special case
	  return a === b || a === $Promise && b === Wrapper;
	};
	var isThenable = function(it){
	  var then;
	  return isObject(it) && typeof (then = it.then) == 'function' ? then : false;
	};
	var newPromiseCapability = function(C){
	  return sameConstructor($Promise, C)
	    ? new PromiseCapability(C)
	    : new GenericPromiseCapability(C);
	};
	var PromiseCapability = GenericPromiseCapability = function(C){
	  var resolve, reject;
	  this.promise = new C(function($$resolve, $$reject){
	    if(resolve !== undefined || reject !== undefined)throw TypeError('Bad Promise constructor');
	    resolve = $$resolve;
	    reject  = $$reject;
	  });
	  this.resolve = aFunction(resolve);
	  this.reject  = aFunction(reject);
	};
	var perform = function(exec){
	  try {
	    exec();
	  } catch(e){
	    return {error: e};
	  }
	};
	var notify = function(promise, isReject){
	  if(promise._n)return;
	  promise._n = true;
	  var chain = promise._c;
	  microtask(function(){
	    var value = promise._v
	      , ok    = promise._s == 1
	      , i     = 0;
	    var run = function(reaction){
	      var handler = ok ? reaction.ok : reaction.fail
	        , resolve = reaction.resolve
	        , reject  = reaction.reject
	        , domain  = reaction.domain
	        , result, then;
	      try {
	        if(handler){
	          if(!ok){
	            if(promise._h == 2)onHandleUnhandled(promise);
	            promise._h = 1;
	          }
	          if(handler === true)result = value;
	          else {
	            if(domain)domain.enter();
	            result = handler(value);
	            if(domain)domain.exit();
	          }
	          if(result === reaction.promise){
	            reject(TypeError('Promise-chain cycle'));
	          } else if(then = isThenable(result)){
	            then.call(result, resolve, reject);
	          } else resolve(result);
	        } else reject(value);
	      } catch(e){
	        reject(e);
	      }
	    };
	    while(chain.length > i)run(chain[i++]); // variable length - can't use forEach
	    promise._c = [];
	    promise._n = false;
	    if(isReject && !promise._h)onUnhandled(promise);
	  });
	};
	var onUnhandled = function(promise){
	  task.call(global, function(){
	    var value = promise._v
	      , abrupt, handler, console;
	    if(isUnhandled(promise)){
	      abrupt = perform(function(){
	        if(isNode){
	          process.emit('unhandledRejection', value, promise);
	        } else if(handler = global.onunhandledrejection){
	          handler({promise: promise, reason: value});
	        } else if((console = global.console) && console.error){
	          console.error('Unhandled promise rejection', value);
	        }
	      });
	      // Browsers should not trigger `rejectionHandled` event if it was handled here, NodeJS - should
	      promise._h = isNode || isUnhandled(promise) ? 2 : 1;
	    } promise._a = undefined;
	    if(abrupt)throw abrupt.error;
	  });
	};
	var isUnhandled = function(promise){
	  if(promise._h == 1)return false;
	  var chain = promise._a || promise._c
	    , i     = 0
	    , reaction;
	  while(chain.length > i){
	    reaction = chain[i++];
	    if(reaction.fail || !isUnhandled(reaction.promise))return false;
	  } return true;
	};
	var onHandleUnhandled = function(promise){
	  task.call(global, function(){
	    var handler;
	    if(isNode){
	      process.emit('rejectionHandled', promise);
	    } else if(handler = global.onrejectionhandled){
	      handler({promise: promise, reason: promise._v});
	    }
	  });
	};
	var $reject = function(value){
	  var promise = this;
	  if(promise._d)return;
	  promise._d = true;
	  promise = promise._w || promise; // unwrap
	  promise._v = value;
	  promise._s = 2;
	  if(!promise._a)promise._a = promise._c.slice();
	  notify(promise, true);
	};
	var $resolve = function(value){
	  var promise = this
	    , then;
	  if(promise._d)return;
	  promise._d = true;
	  promise = promise._w || promise; // unwrap
	  try {
	    if(promise === value)throw TypeError("Promise can't be resolved itself");
	    if(then = isThenable(value)){
	      microtask(function(){
	        var wrapper = {_w: promise, _d: false}; // wrap
	        try {
	          then.call(value, ctx($resolve, wrapper, 1), ctx($reject, wrapper, 1));
	        } catch(e){
	          $reject.call(wrapper, e);
	        }
	      });
	    } else {
	      promise._v = value;
	      promise._s = 1;
	      notify(promise, false);
	    }
	  } catch(e){
	    $reject.call({_w: promise, _d: false}, e); // wrap
	  }
	};

	// constructor polyfill
	if(!USE_NATIVE){
	  // 25.4.3.1 Promise(executor)
	  $Promise = function Promise(executor){
	    anInstance(this, $Promise, PROMISE, '_h');
	    aFunction(executor);
	    Internal.call(this);
	    try {
	      executor(ctx($resolve, this, 1), ctx($reject, this, 1));
	    } catch(err){
	      $reject.call(this, err);
	    }
	  };
	  Internal = function Promise(executor){
	    this._c = [];             // <- awaiting reactions
	    this._a = undefined;      // <- checked in isUnhandled reactions
	    this._s = 0;              // <- state
	    this._d = false;          // <- done
	    this._v = undefined;      // <- value
	    this._h = 0;              // <- rejection state, 0 - default, 1 - handled, 2 - unhandled
	    this._n = false;          // <- notify
	  };
	  Internal.prototype = __webpack_require__(70)($Promise.prototype, {
	    // 25.4.5.3 Promise.prototype.then(onFulfilled, onRejected)
	    then: function then(onFulfilled, onRejected){
	      var reaction    = newPromiseCapability(speciesConstructor(this, $Promise));
	      reaction.ok     = typeof onFulfilled == 'function' ? onFulfilled : true;
	      reaction.fail   = typeof onRejected == 'function' && onRejected;
	      reaction.domain = isNode ? process.domain : undefined;
	      this._c.push(reaction);
	      if(this._a)this._a.push(reaction);
	      if(this._s)notify(this, false);
	      return reaction.promise;
	    },
	    // 25.4.5.1 Promise.prototype.catch(onRejected)
	    'catch': function(onRejected){
	      return this.then(undefined, onRejected);
	    }
	  });
	  PromiseCapability = function(){
	    var promise  = new Internal;
	    this.promise = promise;
	    this.resolve = ctx($resolve, promise, 1);
	    this.reject  = ctx($reject, promise, 1);
	  };
	}

	$export($export.G + $export.W + $export.F * !USE_NATIVE, {Promise: $Promise});
	__webpack_require__(52)($Promise, PROMISE);
	__webpack_require__(71)(PROMISE);
	Wrapper = __webpack_require__(7)[PROMISE];

	// statics
	$export($export.S + $export.F * !USE_NATIVE, PROMISE, {
	  // 25.4.4.5 Promise.reject(r)
	  reject: function reject(r){
	    var capability = newPromiseCapability(this)
	      , $$reject   = capability.reject;
	    $$reject(r);
	    return capability.promise;
	  }
	});
	$export($export.S + $export.F * (LIBRARY || !USE_NATIVE), PROMISE, {
	  // 25.4.4.6 Promise.resolve(x)
	  resolve: function resolve(x){
	    // instanceof instead of internal slot check because we should fix it without replacement native Promise core
	    if(x instanceof $Promise && sameConstructor(x.constructor, this))return x;
	    var capability = newPromiseCapability(this)
	      , $$resolve  = capability.resolve;
	    $$resolve(x);
	    return capability.promise;
	  }
	});
	$export($export.S + $export.F * !(USE_NATIVE && __webpack_require__(72)(function(iter){
	  $Promise.all(iter)['catch'](empty);
	})), PROMISE, {
	  // 25.4.4.1 Promise.all(iterable)
	  all: function all(iterable){
	    var C          = this
	      , capability = newPromiseCapability(C)
	      , resolve    = capability.resolve
	      , reject     = capability.reject;
	    var abrupt = perform(function(){
	      var values    = []
	        , index     = 0
	        , remaining = 1;
	      forOf(iterable, false, function(promise){
	        var $index        = index++
	          , alreadyCalled = false;
	        values.push(undefined);
	        remaining++;
	        C.resolve(promise).then(function(value){
	          if(alreadyCalled)return;
	          alreadyCalled  = true;
	          values[$index] = value;
	          --remaining || resolve(values);
	        }, reject);
	      });
	      --remaining || resolve(values);
	    });
	    if(abrupt)reject(abrupt.error);
	    return capability.promise;
	  },
	  // 25.4.4.4 Promise.race(iterable)
	  race: function race(iterable){
	    var C          = this
	      , capability = newPromiseCapability(C)
	      , reject     = capability.reject;
	    var abrupt = perform(function(){
	      forOf(iterable, false, function(promise){
	        C.resolve(promise).then(capability.resolve, reject);
	      });
	    });
	    if(abrupt)reject(abrupt.error);
	    return capability.promise;
	  }
	});

/***/ },
/* 60 */
/***/ function(module, exports, __webpack_require__) {

	// getting tag from 19.1.3.6 Object.prototype.toString()
	var cof = __webpack_require__(26)
	  , TAG = __webpack_require__(53)('toStringTag')
	  // ES3 wrong here
	  , ARG = cof(function(){ return arguments; }()) == 'Arguments';

	// fallback for IE11 Script Access Denied error
	var tryGet = function(it, key){
	  try {
	    return it[key];
	  } catch(e){ /* empty */ }
	};

	module.exports = function(it){
	  var O, T, B;
	  return it === undefined ? 'Undefined' : it === null ? 'Null'
	    // @@toStringTag case
	    : typeof (T = tryGet(O = Object(it), TAG)) == 'string' ? T
	    // builtinTag case
	    : ARG ? cof(O)
	    // ES3 arguments fallback
	    : (B = cof(O)) == 'Object' && typeof O.callee == 'function' ? 'Arguments' : B;
	};

/***/ },
/* 61 */
/***/ function(module, exports) {

	module.exports = function(it, Constructor, name, forbiddenField){
	  if(!(it instanceof Constructor) || (forbiddenField !== undefined && forbiddenField in it)){
	    throw TypeError(name + ': incorrect invocation!');
	  } return it;
	};

/***/ },
/* 62 */
/***/ function(module, exports, __webpack_require__) {

	var ctx         = __webpack_require__(8)
	  , call        = __webpack_require__(63)
	  , isArrayIter = __webpack_require__(64)
	  , anObject    = __webpack_require__(12)
	  , toLength    = __webpack_require__(29)
	  , getIterFn   = __webpack_require__(65)
	  , BREAK       = {}
	  , RETURN      = {};
	var exports = module.exports = function(iterable, entries, fn, that, ITERATOR){
	  var iterFn = ITERATOR ? function(){ return iterable; } : getIterFn(iterable)
	    , f      = ctx(fn, that, entries ? 2 : 1)
	    , index  = 0
	    , length, step, iterator, result;
	  if(typeof iterFn != 'function')throw TypeError(iterable + ' is not iterable!');
	  // fast case for arrays with default iterator
	  if(isArrayIter(iterFn))for(length = toLength(iterable.length); length > index; index++){
	    result = entries ? f(anObject(step = iterable[index])[0], step[1]) : f(iterable[index]);
	    if(result === BREAK || result === RETURN)return result;
	  } else for(iterator = iterFn.call(iterable); !(step = iterator.next()).done; ){
	    result = call(iterator, f, step.value, entries);
	    if(result === BREAK || result === RETURN)return result;
	  }
	};
	exports.BREAK  = BREAK;
	exports.RETURN = RETURN;

/***/ },
/* 63 */
/***/ function(module, exports, __webpack_require__) {

	// call something on iterator step with safe closing on error
	var anObject = __webpack_require__(12);
	module.exports = function(iterator, fn, value, entries){
	  try {
	    return entries ? fn(anObject(value)[0], value[1]) : fn(value);
	  // 7.4.6 IteratorClose(iterator, completion)
	  } catch(e){
	    var ret = iterator['return'];
	    if(ret !== undefined)anObject(ret.call(iterator));
	    throw e;
	  }
	};

/***/ },
/* 64 */
/***/ function(module, exports, __webpack_require__) {

	// check on default Array iterator
	var Iterators  = __webpack_require__(47)
	  , ITERATOR   = __webpack_require__(53)('iterator')
	  , ArrayProto = Array.prototype;

	module.exports = function(it){
	  return it !== undefined && (Iterators.Array === it || ArrayProto[ITERATOR] === it);
	};

/***/ },
/* 65 */
/***/ function(module, exports, __webpack_require__) {

	var classof   = __webpack_require__(60)
	  , ITERATOR  = __webpack_require__(53)('iterator')
	  , Iterators = __webpack_require__(47);
	module.exports = __webpack_require__(7).getIteratorMethod = function(it){
	  if(it != undefined)return it[ITERATOR]
	    || it['@@iterator']
	    || Iterators[classof(it)];
	};

/***/ },
/* 66 */
/***/ function(module, exports, __webpack_require__) {

	// 7.3.20 SpeciesConstructor(O, defaultConstructor)
	var anObject  = __webpack_require__(12)
	  , aFunction = __webpack_require__(9)
	  , SPECIES   = __webpack_require__(53)('species');
	module.exports = function(O, D){
	  var C = anObject(O).constructor, S;
	  return C === undefined || (S = anObject(C)[SPECIES]) == undefined ? D : aFunction(S);
	};

/***/ },
/* 67 */
/***/ function(module, exports, __webpack_require__) {

	var ctx                = __webpack_require__(8)
	  , invoke             = __webpack_require__(68)
	  , html               = __webpack_require__(51)
	  , cel                = __webpack_require__(17)
	  , global             = __webpack_require__(6)
	  , process            = global.process
	  , setTask            = global.setImmediate
	  , clearTask          = global.clearImmediate
	  , MessageChannel     = global.MessageChannel
	  , counter            = 0
	  , queue              = {}
	  , ONREADYSTATECHANGE = 'onreadystatechange'
	  , defer, channel, port;
	var run = function(){
	  var id = +this;
	  if(queue.hasOwnProperty(id)){
	    var fn = queue[id];
	    delete queue[id];
	    fn();
	  }
	};
	var listener = function(event){
	  run.call(event.data);
	};
	// Node.js 0.9+ & IE10+ has setImmediate, otherwise:
	if(!setTask || !clearTask){
	  setTask = function setImmediate(fn){
	    var args = [], i = 1;
	    while(arguments.length > i)args.push(arguments[i++]);
	    queue[++counter] = function(){
	      invoke(typeof fn == 'function' ? fn : Function(fn), args);
	    };
	    defer(counter);
	    return counter;
	  };
	  clearTask = function clearImmediate(id){
	    delete queue[id];
	  };
	  // Node.js 0.8-
	  if(__webpack_require__(26)(process) == 'process'){
	    defer = function(id){
	      process.nextTick(ctx(run, id, 1));
	    };
	  // Browsers with MessageChannel, includes WebWorkers
	  } else if(MessageChannel){
	    channel = new MessageChannel;
	    port    = channel.port2;
	    channel.port1.onmessage = listener;
	    defer = ctx(port.postMessage, port, 1);
	  // Browsers with postMessage, skip WebWorkers
	  // IE8 has postMessage, but it's sync & typeof its postMessage is 'object'
	  } else if(global.addEventListener && typeof postMessage == 'function' && !global.importScripts){
	    defer = function(id){
	      global.postMessage(id + '', '*');
	    };
	    global.addEventListener('message', listener, false);
	  // IE8-
	  } else if(ONREADYSTATECHANGE in cel('script')){
	    defer = function(id){
	      html.appendChild(cel('script'))[ONREADYSTATECHANGE] = function(){
	        html.removeChild(this);
	        run.call(id);
	      };
	    };
	  // Rest old browsers
	  } else {
	    defer = function(id){
	      setTimeout(ctx(run, id, 1), 0);
	    };
	  }
	}
	module.exports = {
	  set:   setTask,
	  clear: clearTask
	};

/***/ },
/* 68 */
/***/ function(module, exports) {

	// fast apply, http://jsperf.lnkit.com/fast-apply/5
	module.exports = function(fn, args, that){
	  var un = that === undefined;
	  switch(args.length){
	    case 0: return un ? fn()
	                      : fn.call(that);
	    case 1: return un ? fn(args[0])
	                      : fn.call(that, args[0]);
	    case 2: return un ? fn(args[0], args[1])
	                      : fn.call(that, args[0], args[1]);
	    case 3: return un ? fn(args[0], args[1], args[2])
	                      : fn.call(that, args[0], args[1], args[2]);
	    case 4: return un ? fn(args[0], args[1], args[2], args[3])
	                      : fn.call(that, args[0], args[1], args[2], args[3]);
	  } return              fn.apply(that, args);
	};

/***/ },
/* 69 */
/***/ function(module, exports, __webpack_require__) {

	var global    = __webpack_require__(6)
	  , macrotask = __webpack_require__(67).set
	  , Observer  = global.MutationObserver || global.WebKitMutationObserver
	  , process   = global.process
	  , Promise   = global.Promise
	  , isNode    = __webpack_require__(26)(process) == 'process';

	module.exports = function(){
	  var head, last, notify;

	  var flush = function(){
	    var parent, fn;
	    if(isNode && (parent = process.domain))parent.exit();
	    while(head){
	      fn   = head.fn;
	      head = head.next;
	      try {
	        fn();
	      } catch(e){
	        if(head)notify();
	        else last = undefined;
	        throw e;
	      }
	    } last = undefined;
	    if(parent)parent.enter();
	  };

	  // Node.js
	  if(isNode){
	    notify = function(){
	      process.nextTick(flush);
	    };
	  // browsers with MutationObserver
	  } else if(Observer){
	    var toggle = true
	      , node   = document.createTextNode('');
	    new Observer(flush).observe(node, {characterData: true}); // eslint-disable-line no-new
	    notify = function(){
	      node.data = toggle = !toggle;
	    };
	  // environments with maybe non-completely correct, but existent Promise
	  } else if(Promise && Promise.resolve){
	    var promise = Promise.resolve();
	    notify = function(){
	      promise.then(flush);
	    };
	  // for other environments - macrotask based on:
	  // - setImmediate
	  // - MessageChannel
	  // - window.postMessag
	  // - onreadystatechange
	  // - setTimeout
	  } else {
	    notify = function(){
	      // strange IE + webpack dev server bug - use .call(global)
	      macrotask.call(global, flush);
	    };
	  }

	  return function(fn){
	    var task = {fn: fn, next: undefined};
	    if(last)last.next = task;
	    if(!head){
	      head = task;
	      notify();
	    } last = task;
	  };
	};

/***/ },
/* 70 */
/***/ function(module, exports, __webpack_require__) {

	var hide = __webpack_require__(10);
	module.exports = function(target, src, safe){
	  for(var key in src){
	    if(safe && target[key])target[key] = src[key];
	    else hide(target, key, src[key]);
	  } return target;
	};

/***/ },
/* 71 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	var global      = __webpack_require__(6)
	  , core        = __webpack_require__(7)
	  , dP          = __webpack_require__(11)
	  , DESCRIPTORS = __webpack_require__(15)
	  , SPECIES     = __webpack_require__(53)('species');

	module.exports = function(KEY){
	  var C = typeof core[KEY] == 'function' ? core[KEY] : global[KEY];
	  if(DESCRIPTORS && C && !C[SPECIES])dP.f(C, SPECIES, {
	    configurable: true,
	    get: function(){ return this; }
	  });
	};

/***/ },
/* 72 */
/***/ function(module, exports, __webpack_require__) {

	var ITERATOR     = __webpack_require__(53)('iterator')
	  , SAFE_CLOSING = false;

	try {
	  var riter = [7][ITERATOR]();
	  riter['return'] = function(){ SAFE_CLOSING = true; };
	  Array.from(riter, function(){ throw 2; });
	} catch(e){ /* empty */ }

	module.exports = function(exec, skipClosing){
	  if(!skipClosing && !SAFE_CLOSING)return false;
	  var safe = false;
	  try {
	    var arr  = [7]
	      , iter = arr[ITERATOR]();
	    iter.next = function(){ return {done: safe = true}; };
	    arr[ITERATOR] = function(){ return iter; };
	    exec(arr);
	  } catch(e){ /* empty */ }
	  return safe;
	};

/***/ },
/* 73 */
/***/ function(module, exports) {

	module.exports = Vue;

/***/ },
/* 74 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(75)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(81),
	  /* template */
	  __webpack_require__(232),
	  /* scopeId */
	  "data-v-8964a1f8",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 75 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(76);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("96e25fc2", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-8964a1f8&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newLogin.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-8964a1f8&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newLogin.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 76 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".des-login .des-login-page[data-v-8964a1f8]{position:fixed;height:100%;width:100%;top:0;left:0;z-index:101}.des-login .des-login-page .des-login-background[data-v-8964a1f8]{width:100%;height:100%;background:hsla(0,0%,100%,.6)}.des-login .des-login-page .des-login-container[data-v-8964a1f8]{position:absolute;top:161px;left:0;right:0;margin:auto;width:402px;height:366px;background:#fff;box-shadow:0 2px 10px 0 rgba(0,0,0,.8);border-radius:6px}.des-login .des-login-page .des-login-container .top[data-v-8964a1f8]{width:100%;height:60px;position:relative;background:#0ec0b4;border-radius:6px 6px 0 0}.des-login .des-login-page .des-login-container .top .closeBtn[data-v-8964a1f8]{position:absolute;width:24px;height:24px;background:#0ec0b4;border:1px solid #fff;border-radius:100%;color:#fff;line-height:24px;font-size:10px;right:9px;top:9px;margin:auto;cursor:pointer;text-align:center;box-sizing:border-box}.des-login .des-login-page .des-login-container .top .closeBtn[data-v-8964a1f8]:hover{border:1px solid #ff7272;background:#ff7272}.des-login .des-login-page .des-login-container .top .closeBtn i[data-v-8964a1f8]{padding-left:1.5px;line-height:24px}.des-login .des-login-page .des-login-container .top .loginNav[data-v-8964a1f8]{position:absolute;width:144px;height:48px;background:#0ec0b4;font-size:20px;text-align:center;color:#fff;line-height:48px;bottom:0;left:26px;margin:auto;cursor:pointer}.des-login .des-login-page .des-login-container .top .regNav[data-v-8964a1f8]{position:absolute;width:144px;height:48px;background:#0ec0b4;font-size:20px;text-align:center;color:#fff;line-height:48px;bottom:0;right:72px;margin:auto;cursor:pointer}.des-login .des-login-page .des-login-container .top .navActive[data-v-8964a1f8]{background:#fff;box-shadow:0 -2px 4px 0 rgba(0,0,0,.3);border-radius:6px 6px 0 0;color:#333}.des-login .des-login-page .des-login-container .account[data-v-8964a1f8]{position:relative;width:100%;height:34px;margin-top:30px}.des-login .des-login-page .des-login-container .password[data-v-8964a1f8]{position:relative;width:100%;height:34px;margin-top:12px}.des-login .des-login-page .des-login-container .inputArea i[data-v-8964a1f8]{position:absolute;top:0;bottom:0;margin:auto;line-height:34px;color:#4a4a4a;margin-left:34px;z-index:1}.des-login .des-login-page .des-login-container .inputArea i.phoneIcon[data-v-8964a1f8]{font-size:18px}.des-login .des-login-page .des-login-container .inputArea i.codeIcon[data-v-8964a1f8]{font-size:15px}.des-login .des-login-page .des-login-container .inputArea i.passwordIcon[data-v-8964a1f8]{font-size:12px}.des-login .des-login-page .des-login-container .inputArea i.deleteIcon[data-v-8964a1f8]{font-size:12px;right:34px;color:#b7b7b7;cursor:pointer}.des-login .des-login-page .des-login-container .inputArea i.deleteIcon[data-v-8964a1f8]:hover{opacity:.9}.des-login .des-login-page .des-login-container .inputArea input[data-v-8964a1f8]{position:absolute;background:#fdfdfd;border-radius:3px;border:1px solid #efefef;width:350px;height:30px;outline:none;padding-left:28px;font-size:12px;color:#000;line-height:34px;left:0;right:0;top:0;bottom:0;margin:auto}.des-login .des-login-page .des-login-container .inputArea input[data-v-8964a1f8]:hover{border:1px solid #cbcbcb}.des-login .des-login-page .des-login-container .inputArea input[data-v-8964a1f8]:focus{border:1px solid #18ccc0;box-shadow:0 0 3px 0 rgba(105,255,244,.8)}.des-login .des-login-page .des-login-container .inputArea input[data-v-8964a1f8]::-webkit-input-placeholder{color:#4a4a4a}.des-login .des-login-page .des-login-container .inputArea input.error[data-v-8964a1f8]{border:1px solid #f67171}.des-login .des-login-page .des-login-container .inputArea input.error[data-v-8964a1f8]::-webkit-input-placeholder{color:#f67171}.des-login .des-login-page .des-login-container .inputArea .errorTips[data-v-8964a1f8]{position:absolute;font-size:12px;color:#f67171;line-height:33px;right:34px}.des-login .des-login-page .des-login-container .loginErrorTips[data-v-8964a1f8]{position:relative;width:100%;height:29px;font-size:12px;color:#f67171;line-height:29px;text-align:right;padding-right:26px;box-sizing:border-box}.des-login .des-login-page .des-login-container .loginBtn[data-v-8964a1f8],.des-login .des-login-page .des-login-container .regBtn[data-v-8964a1f8]{position:relative;width:100%;height:44px}.des-login .des-login-page .des-login-container .loginBtn .btn[data-v-8964a1f8],.des-login .des-login-page .des-login-container .regBtn .btn[data-v-8964a1f8]{position:absolute;width:350px;height:42px;background:#18ccc0;border:1px solid #18ccc0;border-radius:2px;left:0;right:0;top:0;bottom:0;margin:auto;font-size:14px;color:#fff;line-height:42px;padding:0;text-align:center;cursor:pointer}.des-login .des-login-page .des-login-container .loginBtn .btn[data-v-8964a1f8]:hover,.des-login .des-login-page .des-login-container .regBtn .btn[data-v-8964a1f8]:hover{opacity:.8}.des-login .des-login-page .des-login-container .regBtn[data-v-8964a1f8]{margin-top:25px}.des-login .des-login-page .des-login-container .forgetPassWord[data-v-8964a1f8]{position:relative;padding-left:26px;height:17px;font-size:12px;color:#999;line-height:17px;margin-top:12px}.des-login .des-login-page .des-login-container .forgetPassWord span[data-v-8964a1f8]{cursor:pointer}.des-login .des-login-page .des-login-container .forgetPassWord span[data-v-8964a1f8]:hover{text-decoration:underline}.des-login .des-login-page .des-login-container .thirdLogin[data-v-8964a1f8]{width:348px;height:46px;border-top:1px dashed #d6d6d6;margin:22px 26px 0;padding-top:12px;display:flex}.des-login .des-login-page .des-login-container .thirdLogin .thirdLoginBtn[data-v-8964a1f8]{width:108px;height:34px;border-radius:3px;font-size:14px;color:#fff;line-height:34px;flex:1;cursor:pointer}.des-login .des-login-page .des-login-container .thirdLogin .thirdLoginBtn[data-v-8964a1f8]:hover{opacity:.8}.des-login .des-login-page .des-login-container .thirdLogin i[data-v-8964a1f8]{font-size:18px;margin-left:8px;float:left;margin-top:9px}.des-login .des-login-page .des-login-container .thirdLogin span[data-v-8964a1f8]{margin-left:6px}.des-login .des-login-page .des-login-container .thirdLogin .qq[data-v-8964a1f8]{background:#19b5f7;margin-right:13px}.des-login .des-login-page .des-login-container .thirdLogin .wx[data-v-8964a1f8]{background:#6ed16c;margin-right:13px}.des-login .des-login-page .des-login-container .thirdLogin .sina[data-v-8964a1f8]{background:#f07381}.des-login .des-login-page .des-login-container .tel[data-v-8964a1f8]{position:relative;width:100%;height:34px;margin-top:30px}.des-login .des-login-page .des-login-container .verifyCode[data-v-8964a1f8]{position:relative;width:100%;height:34px;margin-top:12px}.des-login .des-login-page .des-login-container .verifyCode input[data-v-8964a1f8]{left:25px;right:100%;width:210px}.des-login .des-login-page .des-login-container .verifyCode .verifyCodeBtn[data-v-8964a1f8]{position:absolute;width:120px;height:34px;background:#18ccc0;border-radius:2px;font-size:12px;color:#fff;line-height:34px;text-align:center;cursor:pointer;right:26px;top:0;bottom:0;margin:auto}.des-login .des-login-page .des-login-container .verifyCode .disable[data-v-8964a1f8]{background:#ccc}.des-login .des-login-page .des-login-container .passwordReg[data-v-8964a1f8]{position:relative;width:100%;height:51px;margin-top:15px;border-top:1px dashed #d6d6d6}.des-login .des-login-page .des-login-container .passwordReg input[data-v-8964a1f8]{top:14px}.des-login .des-login-page .des-login-container .passwordAgain[data-v-8964a1f8]{position:relative;width:100%;height:34px;margin-top:12px}.des-login .des-login-page .des-login-container .reg-check[data-v-8964a1f8]{position:relative;width:100%;height:12px;font-size:12px;color:#83817b;line-height:16px;margin-top:15px}.des-login .des-login-page .des-login-container .reg-check input[data-v-8964a1f8]{position:absolute;width:12px;height:12px;top:0;bottom:0;left:26px;cursor:pointer}.des-login .des-login-page .des-login-container .reg-check span[data-v-8964a1f8]{margin-left:48px}.des-login .des-login-page .des-login-container .reg-check a[data-v-8964a1f8]{text-decoration:none;color:#5398c0;cursor:pointer}.des-login .des-login-page .des-login-container .reg-check span.regErrorTips[data-v-8964a1f8]{font-size:12px;color:#f67171;float:right;margin-right:26px}.des-login .des-login-page .des-sign-container[data-v-8964a1f8]{height:404px}.phone-verify-box[data-v-8964a1f8]{position:absolute;width:300px;top:260px;left:0;right:0;margin:auto;display:flex;flex-direction:column;justify-content:center;color:#4a4a4a;background:#fff;text-align:center;border:1px solid #999;border-radius:10px;padding:20px 10px;z-index:2}.phone-verify-box .phone-verify-title[data-v-8964a1f8]{line-height:20px}.phone-verify-box .phone-verify-content[data-v-8964a1f8]{margin:20px 0;display:flex;justify-content:center}.phone-verify-box .phone-verify-content .phone-input[data-v-8964a1f8]{width:120px}.phone-verify-box .phone-verify-content .phone-verify-img[data-v-8964a1f8]{width:80px;margin-left:10px}.phone-verify-box .phone-verify-button[data-v-8964a1f8]{width:200px;height:35px;margin:auto;line-height:35px;color:#fff;background:#18ccc0;border-radius:5px;cursor:pointer}.scale-enter-active[data-v-8964a1f8],.scale-leave-active[data-v-8964a1f8]{transition:transform .3s;transform:scale(1)}.scale-enter[data-v-8964a1f8],.scale-leave-active[data-v-8964a1f8]{transform:scale(.8)}.fade-enter-active[data-v-8964a1f8],.fade-leave-active[data-v-8964a1f8]{transition:opacity .3s ease}.fade-enter[data-v-8964a1f8],.fade-leave-active[data-v-8964a1f8]{opacity:0}", ""]);

	// exports


/***/ },
/* 77 */
/***/ function(module, exports) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	// css base code, injected by the css-loader
	module.exports = function() {
		var list = [];

		// return the list of modules as css string
		list.toString = function toString() {
			var result = [];
			for(var i = 0; i < this.length; i++) {
				var item = this[i];
				if(item[2]) {
					result.push("@media " + item[2] + "{" + item[1] + "}");
				} else {
					result.push(item[1]);
				}
			}
			return result.join("");
		};

		// import a list of modules into the list
		list.i = function(modules, mediaQuery) {
			if(typeof modules === "string")
				modules = [[null, modules, ""]];
			var alreadyImportedModules = {};
			for(var i = 0; i < this.length; i++) {
				var id = this[i][0];
				if(typeof id === "number")
					alreadyImportedModules[id] = true;
			}
			for(i = 0; i < modules.length; i++) {
				var item = modules[i];
				// skip already imported module
				// this implementation is not 100% perfect for weird media query combinations
				//  when a module is imported multiple times with different media queries.
				//  I hope this will never occur (Hey this way we have smaller bundles)
				if(typeof item[0] !== "number" || !alreadyImportedModules[item[0]]) {
					if(mediaQuery && !item[2]) {
						item[2] = mediaQuery;
					} else if(mediaQuery) {
						item[2] = "(" + item[2] + ") and (" + mediaQuery + ")";
					}
					list.push(item);
				}
			}
		};
		return list;
	};


/***/ },
/* 78 */
/***/ function(module, exports, __webpack_require__) {

	/*
	  MIT License http://www.opensource.org/licenses/mit-license.php
	  Author Tobias Koppers @sokra
	  Modified by Evan You @yyx990803
	*/

	var hasDocument = typeof document !== 'undefined'

	if (false) {
	  if (!hasDocument) {
	    throw new Error(
	    'vue-style-loader cannot be used in a non-browser environment. ' +
	    "Use { target: 'node' } in your Webpack config to indicate a server-rendering environment."
	  ) }
	}

	var listToStyles = __webpack_require__(79)

	/*
	type StyleObject = {
	  id: number;
	  parts: Array<StyleObjectPart>
	}

	type StyleObjectPart = {
	  css: string;
	  media: string;
	  sourceMap: ?string
	}
	*/

	var stylesInDom = {/*
	  [id: number]: {
	    id: number,
	    refs: number,
	    parts: Array<(obj?: StyleObjectPart) => void>
	  }
	*/}

	var head = hasDocument && (document.head || document.getElementsByTagName('head')[0])
	var singletonElement = null
	var singletonCounter = 0
	var isProduction = false
	var noop = function () {}

	// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
	// tags it will allow on a page
	var isOldIE = typeof navigator !== 'undefined' && /msie [6-9]\b/.test(navigator.userAgent.toLowerCase())

	module.exports = function (parentId, list, _isProduction) {
	  isProduction = _isProduction

	  var styles = listToStyles(parentId, list)
	  addStylesToDom(styles)

	  return function update (newList) {
	    var mayRemove = []
	    for (var i = 0; i < styles.length; i++) {
	      var item = styles[i]
	      var domStyle = stylesInDom[item.id]
	      domStyle.refs--
	      mayRemove.push(domStyle)
	    }
	    if (newList) {
	      styles = listToStyles(parentId, newList)
	      addStylesToDom(styles)
	    } else {
	      styles = []
	    }
	    for (var i = 0; i < mayRemove.length; i++) {
	      var domStyle = mayRemove[i]
	      if (domStyle.refs === 0) {
	        for (var j = 0; j < domStyle.parts.length; j++) {
	          domStyle.parts[j]()
	        }
	        delete stylesInDom[domStyle.id]
	      }
	    }
	  }
	}

	function addStylesToDom (styles /* Array<StyleObject> */) {
	  for (var i = 0; i < styles.length; i++) {
	    var item = styles[i]
	    var domStyle = stylesInDom[item.id]
	    if (domStyle) {
	      domStyle.refs++
	      for (var j = 0; j < domStyle.parts.length; j++) {
	        domStyle.parts[j](item.parts[j])
	      }
	      for (; j < item.parts.length; j++) {
	        domStyle.parts.push(addStyle(item.parts[j]))
	      }
	      if (domStyle.parts.length > item.parts.length) {
	        domStyle.parts.length = item.parts.length
	      }
	    } else {
	      var parts = []
	      for (var j = 0; j < item.parts.length; j++) {
	        parts.push(addStyle(item.parts[j]))
	      }
	      stylesInDom[item.id] = { id: item.id, refs: 1, parts: parts }
	    }
	  }
	}

	function listToStyles (parentId, list) {
	  var styles = []
	  var newStyles = {}
	  for (var i = 0; i < list.length; i++) {
	    var item = list[i]
	    var id = item[0]
	    var css = item[1]
	    var media = item[2]
	    var sourceMap = item[3]
	    var part = { css: css, media: media, sourceMap: sourceMap }
	    if (!newStyles[id]) {
	      part.id = parentId + ':0'
	      styles.push(newStyles[id] = { id: id, parts: [part] })
	    } else {
	      part.id = parentId + ':' + newStyles[id].parts.length
	      newStyles[id].parts.push(part)
	    }
	  }
	  return styles
	}

	function createStyleElement () {
	  var styleElement = document.createElement('style')
	  styleElement.type = 'text/css'
	  head.appendChild(styleElement)
	  return styleElement
	}

	function addStyle (obj /* StyleObjectPart */) {
	  var update, remove
	  var styleElement = document.querySelector('style[data-vue-ssr-id~="' + obj.id + '"]')
	  var hasSSR = styleElement != null

	  // if in production mode and style is already provided by SSR,
	  // simply do nothing.
	  if (hasSSR && isProduction) {
	    return noop
	  }

	  if (isOldIE) {
	    // use singleton mode for IE9.
	    var styleIndex = singletonCounter++
	    styleElement = singletonElement || (singletonElement = createStyleElement())
	    update = applyToSingletonTag.bind(null, styleElement, styleIndex, false)
	    remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true)
	  } else {
	    // use multi-style-tag mode in all other cases
	    styleElement = styleElement || createStyleElement()
	    update = applyToTag.bind(null, styleElement)
	    remove = function () {
	      styleElement.parentNode.removeChild(styleElement)
	    }
	  }

	  if (!hasSSR) {
	    update(obj)
	  }

	  return function updateStyle (newObj /* StyleObjectPart */) {
	    if (newObj) {
	      if (newObj.css === obj.css &&
	          newObj.media === obj.media &&
	          newObj.sourceMap === obj.sourceMap) {
	        return
	      }
	      update(obj = newObj)
	    } else {
	      remove()
	    }
	  }
	}

	var replaceText = (function () {
	  var textStore = []

	  return function (index, replacement) {
	    textStore[index] = replacement
	    return textStore.filter(Boolean).join('\n')
	  }
	})()

	function applyToSingletonTag (styleElement, index, remove, obj) {
	  var css = remove ? '' : obj.css

	  if (styleElement.styleSheet) {
	    styleElement.styleSheet.cssText = replaceText(index, css)
	  } else {
	    var cssNode = document.createTextNode(css)
	    var childNodes = styleElement.childNodes
	    if (childNodes[index]) styleElement.removeChild(childNodes[index])
	    if (childNodes.length) {
	      styleElement.insertBefore(cssNode, childNodes[index])
	    } else {
	      styleElement.appendChild(cssNode)
	    }
	  }
	}

	function applyToTag (styleElement, obj) {
	  var css = obj.css
	  var media = obj.media
	  var sourceMap = obj.sourceMap

	  if (media) {
	    styleElement.setAttribute('media', media)
	  }

	  if (sourceMap) {
	    // https://developer.chrome.com/devtools/docs/javascript-debugging
	    // this makes source maps inside style tags work properly in Chrome
	    css += '\n/*# sourceURL=' + sourceMap.sources[0] + ' */'
	    // http://stackoverflow.com/a/26603875
	    css += '\n/*# sourceMappingURL=data:application/json;base64,' + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + ' */'
	  }

	  if (styleElement.styleSheet) {
	    styleElement.styleSheet.cssText = css
	  } else {
	    while (styleElement.firstChild) {
	      styleElement.removeChild(styleElement.firstChild)
	    }
	    styleElement.appendChild(document.createTextNode(css))
	  }
	}


/***/ },
/* 79 */
/***/ function(module, exports) {

	/**
	 * Translates the list format produced by css-loader into something
	 * easier to manipulate.
	 */
	module.exports = function listToStyles (parentId, list) {
	  var styles = []
	  var newStyles = {}
	  for (var i = 0; i < list.length; i++) {
	    var item = list[i]
	    var id = item[0]
	    var css = item[1]
	    var media = item[2]
	    var sourceMap = item[3]
	    var part = {
	      id: parentId + ':' + i,
	      css: css,
	      media: media,
	      sourceMap: sourceMap
	    }
	    if (!newStyles[id]) {
	      styles.push(newStyles[id] = { id: id, parts: [part] })
	    } else {
	      newStyles[id].parts.push(part)
	    }
	  }
	  return styles
	}


/***/ },
/* 80 */
/***/ function(module, exports) {

	module.exports = function normalizeComponent (
	  rawScriptExports,
	  compiledTemplate,
	  scopeId,
	  cssModules
	) {
	  var esModule
	  var scriptExports = rawScriptExports = rawScriptExports || {}

	  // ES6 modules interop
	  var type = typeof rawScriptExports.default
	  if (type === 'object' || type === 'function') {
	    esModule = rawScriptExports
	    scriptExports = rawScriptExports.default
	  }

	  // Vue.extend constructor export interop
	  var options = typeof scriptExports === 'function'
	    ? scriptExports.options
	    : scriptExports

	  // render functions
	  if (compiledTemplate) {
	    options.render = compiledTemplate.render
	    options.staticRenderFns = compiledTemplate.staticRenderFns
	  }

	  // scopedId
	  if (scopeId) {
	    options._scopeId = scopeId
	  }

	  // inject cssModules
	  if (cssModules) {
	    var computed = options.computed || (options.computed = {})
	    Object.keys(cssModules).forEach(function (key) {
	      var module = cssModules[key]
	      computed[key] = function () { return module }
	    })
	  }

	  return {
	    esModule: esModule,
	    exports: scriptExports,
	    options: options
	  }
	}


/***/ },
/* 81 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _extends2 = __webpack_require__(1);

	var _extends3 = _interopRequireDefault(_extends2);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _regExp = __webpack_require__(107);

	var _regExp2 = _interopRequireDefault(_regExp);

	var _forgetPassword = __webpack_require__(108);

	var _forgetPassword2 = _interopRequireDefault(_forgetPassword);

	var _newIdentifyModel = __webpack_require__(113);

	var _newIdentifyModel2 = _interopRequireDefault(_newIdentifyModel);

	var _primaryIdentifyModel = __webpack_require__(122);

	var _primaryIdentifyModel2 = _interopRequireDefault(_primaryIdentifyModel);

	var _seniorIdentityModel = __webpack_require__(129);

	var _seniorIdentityModel2 = _interopRequireDefault(_seniorIdentityModel);

	var _newMessageBox = __webpack_require__(226);

	var _newMessageBox2 = _interopRequireDefault(_newMessageBox);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	  name: 'new-Login',
	  data: function data() {
	    return {
	      show: {
	        isLogin: true,
	        isShow: false,
	        isForget: false,
	        showIdentifyModel: false,
	        primaryIdentify: false,
	        seniorIdentity: false
	      },
	      accountLogin: '',
	      passwordLogin: '',
	      passwordReg: '',
	      passwordAgain: '',
	      verifyCode: '',
	      accountReg: '',
	      regErrorTips: '',
	      accountLoginErrorTips: '',
	      passwordLoginErrorTips: '',
	      loginErrorTips: '',
	      accountRegErrorTips: '',
	      confirmModal: {
	        show: false,
	        callback: 'closeConfirm',
	        title: '温馨提示',
	        content: 'MAKA可以使用手机号登录啦！<br />绑定过安全手机的用户，即可使用原有账号外，还可以使用安全手机+密码登录MAKA'
	      },
	      verifyCodeErrorTips: '',
	      passwordRegErrorTips: '',
	      passwordAgainErrorTips: '',
	      getcode: '获取验证码',
	      counting: false,
	      signup: '提交注册信息>',
	      regCheck: true,
	      needVerify: false,
	      phoneVerifyCode: '',
	      phoneVerifyImg: '',
	      phoneVerifyUid: '',
	      successCallback: null,
	      canClose: true,
	      loginUrl: null,
	      loginOption: null
	    };
	  },

	  components: {
	    'forget-passWord': _forgetPassword2.default,
	    'new-identify-model': _newIdentifyModel2.default,
	    'primary-identify-model': _primaryIdentifyModel2.default,
	    'senior-identity-model': _seniorIdentityModel2.default,
	    'new-message-box': _newMessageBox2.default
	  },
	  created: function created() {},

	  computed: {},
	  watch: {},
	  mounted: function mounted() {
	    var self = this;
	    // self.resetFocus()
	    this.$watch('show.isLogin', function (newVal, oldVal) {
	      self.resetFocus();
	    });
	    this.$watch('show.isShow', function (newVal, oldVal) {
	      self.resetFocus();
	    });
	  },

	  methods: {
	    resetFocus: function resetFocus() {
	      var self = this;
	      self.needVerify = false;
	      self.passwordLoginErrorTips = '';
	      self.accountLoginErrorTips = '';
	      self.loginErrorTips = '';
	      self.accountRegErrorTips = '';
	      self.regErrorTips = '';
	      self.verifyCodeErrorTips = '';
	      self.regErrorTips = '';
	      self.passwordRegErrorTips = '';
	      self.regErrorTips = '';
	      self.passwordAgainErrorTips = '';
	      self.regErrorTips = '';
	    },
	    clearErrorTip: function clearErrorTip(etype) {
	      var errorTipsName = etype + 'ErrorTips';
	      this[errorTipsName] = '';
	      this.regErrorTips = '';
	      this.loginErrorTips = '';
	    },
	    clearPassword: function clearPassword(key) {
	      if (key === 'login') {
	        this.passwordLogin = '';
	      } else if (key === 'reg') {
	        this.passwordReg = '';
	      } else {
	        this.passwordAgain = '';
	      }
	    },
	    close: function close() {
	      console.log('here emit');
	      this.show.isShow = false;
	      this.show.showIdentifyModel = false;
	      this.show.primaryIdentify = false;
	      this.show.seniorIdentity = false;
	    },
	    checkAccount: function checkAccount(ac, login) {
	      var self = this;
	      if (login) {
	        if (ac) {
	          return true;
	        } else {
	          self.$set(this, 'accountLoginErrorTips', '请填写手机号/邮箱');
	          return false;
	        }
	      } else {
	        if (!_regExp2.default.mobile.test(ac)) {
	          self.accountRegErrorTips = '请填写正确的手机号';
	          return false;
	        }
	        return true;
	      }
	    },
	    checkPassword: function checkPassword(pw, login) {
	      var self = this;
	      var msg = '请输入6到20位密码';
	      if (!pw) {
	        if (login) {
	          self.passwordLoginErrorTips = msg;
	        } else {
	          self.passwordRegErrorTips = msg;
	        }
	        return false;
	      }
	      if (!_regExp2.default.password.test(pw)) {
	        if (login) {
	          self.passwordLoginErrorTips = msg;
	        } else {
	          self.passwordRegErrorTips = msg;
	        }
	        return false;
	      }
	      return true;
	    },
	    checkPasswordAg: function checkPasswordAg() {
	      var self = this;
	      if (self.passwordAgain != self.passwordReg) {
	        self.passwordAgainErrorTips = '密码不一致';
	        return false;
	      }
	      return true;
	    },
	    checkVerifyCode: function checkVerifyCode() {
	      if (!this.verifyCode) {
	        this.verifyCodeErrorTips = '请输入验证码';
	        return false;
	      } else {
	        return true;
	      }
	    },
	    thirdLogin: function thirdLogin(way) {
	      var self = this;
	      var urls = {
	        'qq': _config2.default.THIRD_LOGIN_QQ,
	        'weixin': _config2.default.THIRD_LOGIN_WEIXIN,
	        'weibo': _config2.default.THIRD_LOGIN_WEIBO
	      };
	      _utils2.default.setCookie('from', location.pathname);
	      location.href = urls[way];
	    },

	    toLogin: function toLogin(e) {
	      e.preventDefault();
	      var self = this;
	      if (self.checkAccount(self.accountLogin, true) && self.checkPassword(self.passwordLogin, true)) {
	        $.ajax({
	          // 帮助中心登录 or 新登录接口
	          url: self.loginUrl || _config2.default.API_USER_LOGIN,
	          type: 'POST',
	          dataType: 'json',
	          // 新登录接口带有新参数
	          data: self.loginOption ? (0, _extends3.default)({
	            username: self.accountLogin,
	            password: self.passwordLogin
	          }, self.loginOption) : {
	            username: self.accountLogin,
	            password: self.passwordLogin
	          },
	          success: function success(res) {
	            var msg = res.message;
	            if (res.code === 200) {
	              self.userInfo = res.data;
	              // if( res.data.check_tel == 0 ){

	              // }else{
	              // 判断是否是安全手机迁移的手机号，是则弹个框
	              // if( res.data.from_business == 1 ){
	              self.loginErrorTips = '登录成功';
	              // Util.setCookie('token', res.data.token)
	              console.log('登录成功');
	              self.show.isShow = false;
	              // self.show.isSuccess = true
	              setTimeout(function () {
	                if (self.successCallback) {
	                  self.successCallback(res.data);
	                } else {
	                  location.reload();
	                }
	              }, 400);
	              // }
	            } else {
	              self.loginErrorTips = msg;
	            }
	          },
	          error: function error(_error) {
	            console.log(_error);
	          }
	        });
	      }
	    },
	    resetTimer: function resetTimer(msg) {
	      clearInterval(this.timer);
	      this.getcode = msg;
	      this.counting = false;
	    },
	    getVerifyCode: function getVerifyCode() {
	      var self = this;
	      if (self.counting || !self.accountReg) {
	        return;
	      }
	      if (self.checkAccount(self.accountReg, false)) {
	        this.getcode = '获取中...';
	        $.ajax({
	          url: _config2.default.API_REG_VERIFY_CODE,
	          type: 'POST',
	          dataType: 'json',
	          data: {
	            mobile: self.accountReg
	          },
	          success: function success(result) {
	            var msg = result.message;
	            //接口返回一些错误码，不进行读数操作，直接返回
	            if (result.code == 200) {
	              //设置读秒内容，置灰
	              var second = 60;
	              self.counting = true;
	              self.getcode = second + "s后重试";

	              self.timer = setInterval(function () {
	                if (second == 0) {
	                  self.resetTimer('重新获取');
	                } else {
	                  second--;
	                  self.getcode = second + "s后重试";
	                }
	              }, 1000);
	            } else if (result.code == 907) {
	              self.needVerify = true;
	              self.phoneVerifyImg = 'data:image/png;base64,' + result.data.jpeg;
	              self.phoneVerifyUid = result.data.uuid;
	            } else {
	              self.accountRegErrorTips = msg;
	              self.resetTimer('重新获取');
	            }
	          },
	          error: function error(_error2) {
	            self.accountRegErrorTips = '网络错误';
	            self.resetTimer('重新获取');
	          }
	        });
	      }
	    },

	    toReg: function toReg(e) {
	      var self = this;
	      if (!self.regCheck) {
	        self.$set('regErrorTips', '请同意MAKA用户服务协议');
	        return;
	      }
	      if (self.checkAccount(self.accountReg, false) && self.checkVerifyCode() && self.checkPassword(self.passwordReg, false) && self.checkPasswordAg()) {
	        var source = _utils2.default.getParam('source') ? _utils2.default.getParam('source') : 'MAKA';
	        self.signup = '注册中...';
	        $.ajax({
	          url: _config2.default.API_USER_REG_MOBILE,
	          type: 'POST',
	          dataType: 'json',
	          data: {
	            username: self.accountReg,
	            password: self.passwordReg,
	            source: source,
	            code: self.verifyCode
	          },
	          success: function success(result) {
	            var msg = result.message;
	            if (result.code === 200) {
	              self.regErrorTips = '注册成功';
	              self.show.isShow = false;
	              setTimeout(function () {
	                if (self.successCallback) {
	                  self.successCallback(result.data);
	                } else {
	                  self.show.isShow = false;
	                  self.show.showIdentifyModel = true;
	                }
	              }, 400);
	            } else {
	              self.regErrorTips = msg;
	              self.signup = '提交注册信息>';
	            }
	          },
	          error: function error(_error3) {
	            self.regErrorTips = '网络错误';
	          }
	        });
	      }
	    },
	    changeState: function changeState(state) {
	      this.show.isLogin = state;
	    },
	    showForgetPassword: function showForgetPassword() {
	      this.show.isShow = false;
	      this.show.isForget = true;
	    },
	    pushPhoneVerify: function pushPhoneVerify() {
	      var self = this;
	      if (!self.phoneVerifyCode) return;

	      $.ajax({
	        url: '/api/verifycode/reg',
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          mobile: self.accountReg,
	          code: self.phoneVerifyCode
	        },
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code == 200) {
	            self.needVerify = false;
	            var second = 60;
	            self.counting = true;
	            self.getcode = second + "s后重试";

	            self.timer = setInterval(function () {
	              if (second == 0) {
	                self.resetTimer('重新获取');
	              } else {
	                second--;
	                self.getcode = second + "s后重试";
	              }
	            }, 1000);
	          } else if (result.code == 907) {
	            self.phoneVerifyImg = 'data:image/png;base64,' + result.data.jpeg;
	            self.phoneVerifyUid = result.data.uuid;
	          } else {
	            self.needVerify = false;
	            self.accountRegErrorTips = msg;
	            self.resetTimer('重新获取');
	          }
	        },
	        error: function error(_error4) {
	          console.log(_error4);
	        }
	      });
	    }
	  }
	};

/***/ },
/* 82 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _defineProperty2 = __webpack_require__(83);

	var _defineProperty3 = _interopRequireDefault(_defineProperty2);

	var _Config;

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var makaUrl =  true ? 'http://maka.im' : 'http://test.maka.im';
	var viewerUrl =  true ? 'http://viewer.maka.im' : 'http://test.viewer.maka.im';

	var CN_OSS = {
	  ENDPOINT: 'http://oss-cn-beijing.aliyuncs.com',
	  ENDPOINT_TEST: 'http://maka-test.oss-cn-beijing.aliyuncs.com/',
	  ENDPOINT_PROD: 'http://makapicture.oss-cn-beijing.aliyuncs.com/',
	  BUCKET_TEST: 'maka-test',
	  BUCKET_PROD: 'makapicture',
	  CROPCDN_TEST: 'http://test.img1.maka.im/',
	  CROPCDN_PROD: 'http://img2.maka.im/'
	};

	var ENV = {
	  DEV: 'DEVELOPMENT',
	  TEST: 'TEST',
	  PROD: 'PRODUCTION'
	};

	//Chrome弹框和下载统计
	var ADMIN_MAKA_MOBI = {
	  CHROME_TEST: 'http://test.admin.maka.mobi/plat/index.php/api/chrome',
	  CHROME_PROD: 'http://admin.maka.mobi/plat/index.php/api/chrome'
	};

	var Config = (_Config = {
	  API_USERINFO_UPLOAD: '/api/user/',
	  API_HAS_FOLLOW_LIST: '/api/v4/designer/homepage_pri_list?simple=1',
	  API_DESIFNER_FOLLOW_ADD: '/api/v4/designer/add_homepage_pri_list/',
	  API_DESIFNER_FOLLOW_DEL: '/api/v4/designer/del_homepage_pri_list/',
	  API_LOAD_DESIGNER_PROJECT: '/api/v4/designer/homepage_detail_template_list/',
	  API_LOAD_DESIGNER_LIST: '/api/v4/designer/homepage_sub_list/1',
	  API_MY_COLLECTION: '/api/v4/designer/homepage_pri_list',

	  // 模板商城
	  API_STORETEMPLATES: '/api/storeTemplates',
	  API_EVENT: '/api/event/',
	  API_V4_USETEMPLATE: '/api/v4/use_template/',
	  API_TEMPLATE_INFO: '/api/v4/template/store_template_info/',

	  // 支持新tags商城模板获取接口
	  API_STORE_TEMPLATES: '/api/v1/store_templates',

	  // 模板支付
	  API_BUYTEMPLATE: makaUrl + '/api/buyTemplate/',
	  API_USETEMPLATE: makaUrl + '/api/useTemplate/'
	}, (0, _defineProperty3.default)(_Config, 'API_V4_USETEMPLATE', '/api/v4/use_template/'), (0, _defineProperty3.default)(_Config, 'API_BUYTEMPLATEBYALI', '/api/payBuyTemplate/'), (0, _defineProperty3.default)(_Config, 'API_BUSINESS_INFO', '/api/v4/business'), (0, _defineProperty3.default)(_Config, 'API_RECHARGE', '/api/recharge'), (0, _defineProperty3.default)(_Config, 'API_USERCOUPON', '/api/coupon/userCoupon'), (0, _defineProperty3.default)(_Config, 'API_VIEWER_URL', viewerUrl + '/k/'), (0, _defineProperty3.default)(_Config, 'THIRD_LOGIN_QQ', '/user/oauth_login/qq'), (0, _defineProperty3.default)(_Config, 'THIRD_LOGIN_WEIXIN', '/user/oauth_login/wechat'), (0, _defineProperty3.default)(_Config, 'THIRD_LOGIN_WEIBO', '/user/oauth_login/weibo'), (0, _defineProperty3.default)(_Config, 'API_USER_LOGIN', '/api/user/login'), (0, _defineProperty3.default)(_Config, 'API_REG_VERIFY_CODE', '/api/verifycode/reg'), (0, _defineProperty3.default)(_Config, 'API_USER_REG_MOBILE', '/api/v4/user/register'), (0, _defineProperty3.default)(_Config, 'API_RESET_VERIFY_CODE', '/api/verifycode/reset'), (0, _defineProperty3.default)(_Config, 'API_RESET_CHECK_VERIFY_CODE', '/api/user/verify_reset_code'), (0, _defineProperty3.default)(_Config, 'API_FINDPWD', '/api/user/password'), (0, _defineProperty3.default)(_Config, 'API_V4_FINDPWD', '/api/v4/user/password'), (0, _defineProperty3.default)(_Config, 'API_NEW_USER_LOGIN', '/api/v1/sessions'), (0, _defineProperty3.default)(_Config, 'API_NEW_REG_MOBILE', '/api/v1/users'), (0, _defineProperty3.default)(_Config, 'API_USER_BIND_PHONE', '/api/user/bind_phone'), (0, _defineProperty3.default)(_Config, 'API_ADMIN_VERIFY', '/api/verifycode/admin'), (0, _defineProperty3.default)(_Config, 'API_V4_BUSINESS_SET_BUSINESS_IDENTITY', '/api/v4/business/set_business_identity'), (0, _defineProperty3.default)(_Config, 'API_V4_BUSINESS_PRIMARY_VERIFY', '/api/v4/business/primary_verify'), (0, _defineProperty3.default)(_Config, 'API_V4_BUSINESS_SENIOR_VERIFY', '/api/v4/business/senior_verify'), (0, _defineProperty3.default)(_Config, 'API_V4_BUSINESS_SENIOR_OPTION_LIST', '/api/v4/business/senior_option_list'), (0, _defineProperty3.default)(_Config, 'API_V4_BUSINESS_VERIFY_STATUS', '/api/v4/business/verify_status'), (0, _defineProperty3.default)(_Config, 'API_V4_BUSINESS_INDUSTRY_LIST', '/api/v4/business/industry_list'), (0, _defineProperty3.default)(_Config, 'API_STSDATA_2', '/api/ossSts2'), (0, _defineProperty3.default)(_Config, 'API_SEARCH_RESULT', '/api/v1/store_templates'), (0, _defineProperty3.default)(_Config, 'API_RECOMMEND_RESULT', '/api/v1/recommended_templates'), (0, _defineProperty3.default)(_Config, 'API_MESSAGE_POPS', '/api/messagePops'), (0, _defineProperty3.default)(_Config, 'API_MESSAGE_MARKING', '/api/messageMarking'), (0, _defineProperty3.default)(_Config, 'Oss', getOss()), _Config);

	function getEnv() {
	  var host = window.location.host;
	  var env;
	  if (host.indexOf('dev.') > -1) {
	    env = ENV.DEV;
	  } else if (host.indexOf('test.') > -1) {
	    env = ENV.TEST;
	  } else {
	    env = ENV.PROD;
	  }
	  return env;
	}

	function getOss() {
	  var OSS = CN_OSS;
	  var env = getEnv();

	  return {
	    endpoint: OSS.ENDPOINT,
	    // endpoint: env === ENV.PROD ? OSS.ENDPOINT_PROD : OSS.ENDPOINT_TEST,
	    bucketEndpoint: env === ENV.PROD ? OSS.ENDPOINT_PROD : OSS.ENDPOINT_TEST,
	    bucket: env === ENV.PROD ? OSS.BUCKET_PROD : OSS.BUCKET_TEST,
	    cropcdn: env === ENV.PROD ? OSS.CROPCDN_PROD : OSS.CROPCDN_TEST,
	    adminChrome: env === ENV.PROD ? ADMIN_MAKA_MOBI.CHROME_PROD : ADMIN_MAKA_MOBI.CHROME_TEST
	  };
	}

	exports.default = Config;

/***/ },
/* 83 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	exports.__esModule = true;

	var _defineProperty = __webpack_require__(84);

	var _defineProperty2 = _interopRequireDefault(_defineProperty);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = function (obj, key, value) {
	  if (key in obj) {
	    (0, _defineProperty2.default)(obj, key, {
	      value: value,
	      enumerable: true,
	      configurable: true,
	      writable: true
	    });
	  } else {
	    obj[key] = value;
	  }

	  return obj;
	};

/***/ },
/* 84 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(85), __esModule: true };

/***/ },
/* 85 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(86);
	var $Object = __webpack_require__(7).Object;
	module.exports = function defineProperty(it, key, desc){
	  return $Object.defineProperty(it, key, desc);
	};

/***/ },
/* 86 */
/***/ function(module, exports, __webpack_require__) {

	var $export = __webpack_require__(5);
	// 19.1.2.4 / 15.2.3.6 Object.defineProperty(O, P, Attributes)
	$export($export.S + $export.F * !__webpack_require__(15), 'Object', {defineProperty: __webpack_require__(11).f});

/***/ },
/* 87 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _typeof2 = __webpack_require__(88);

	var _typeof3 = _interopRequireDefault(_typeof2);

	var _classCallCheck2 = __webpack_require__(105);

	var _classCallCheck3 = _interopRequireDefault(_classCallCheck2);

	var _createClass2 = __webpack_require__(106);

	var _createClass3 = _interopRequireDefault(_createClass2);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var utils = function () {
	  function utils() {
	    (0, _classCallCheck3.default)(this, utils);
	  }

	  (0, _createClass3.default)(utils, [{
	    key: 'getUrlHash',
	    value: function getUrlHash() {
	      // hash值以';'分隔
	      // 如：to=dataStatistics => 数据统计
	      // 如：pid=12312312 => 作品ID
	      var hash = window.location.hash.substr(1).split(';');
	      var o = {};
	      var temp = void 0;
	      hash.forEach(function (item) {
	        temp = item.split('=');
	        if (temp.length == 2) {
	          o[temp[0]] = temp[1];
	        }
	      });
	      return o;
	    }
	  }, {
	    key: 'getParam',
	    value: function getParam(name) {
	      var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	      var r = window.location.search.substr(1).match(reg);

	      if (r != null) {
	        return unescape(r[2]);
	      } else {
	        return null;
	      }
	    }
	  }, {
	    key: 'getRequest',
	    value: function getRequest() {
	      var url = location.search; //获取url中"?"符后的字串 
	      var theRequest = new Object();
	      if (url.indexOf("?") != -1) {
	        var str = url.substr(1);
	        var strs = str.split("&");
	        for (var i = 0; i < strs.length; i++) {
	          theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
	        }
	      }
	      return theRequest;
	    }
	  }, {
	    key: 'setCookie',
	    value: function setCookie(name, value, expires, path, domain) {
	      var Days = 10;
	      var exp = new Date();
	      var pa = path ? path : '/';
	      if (expires) {
	        exp.setTime(exp.getTime() + parseInt(expires));
	      } else {
	        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	      }
	      document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=" + pa + (domain ? ';domain=' + domain : "");
	    }
	  }, {
	    key: 'getCookie',
	    value: function getCookie(name) {
	      var arr,
	          reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	      if (arr = document.cookie.match(reg)) return unescape(arr[2]);else return null;
	    }
	  }, {
	    key: 'clearCookie',
	    value: function clearCookie() {
	      var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
	      if (keys) {
	        for (var i = keys.length; i--;) {
	          if (keys[i].indexOf('_lastNotice') > -1) {
	            // 不清除红点的标识
	            continue;
	          }
	          document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
	          document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString() + ';path=/;domain=.maka.im';
	          document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString() + ';path=/;domain=.' + location.host;
	        }
	      }
	    }
	  }, {
	    key: 'deleteCookie',
	    value: function deleteCookie(name) {
	      var self = this;
	      self.setCookie(name, "", -1);
	    }
	  }, {
	    key: 'removeCookie',
	    value: function removeCookie(sKey, sPath, sDomain) {

	      if (typeof sKey === "string") {
	        document.cookie = encodeURIComponent(sKey) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT" + (sDomain ? "; domain=" + sDomain : "") + (sPath ? "; path=" + sPath : "");
	      } else if ((typeof sKey === 'undefined' ? 'undefined' : (0, _typeof3.default)(sKey)) === "object") {
	        for (var i in sKey) {
	          document.cookie = encodeURIComponent(sKey[i]) + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT" + (sDomain ? "; domain=" + sDomain : "") + (sPath ? "; path=" + sPath : "");
	        }
	      }
	    }
	  }, {
	    key: 'convertBase64ToBlob',
	    value: function convertBase64ToBlob(base64) {
	      var format = "image/jpeg";
	      var code = window.atob(base64.split(",")[1]);
	      var aBuffer = new window.ArrayBuffer(code.length);
	      var uBuffer = new window.Uint8Array(aBuffer);
	      for (var i = 0; i < code.length; i++) {
	        uBuffer[i] = code.charCodeAt(i);
	      }
	      var Builder = window.WebKitBlobBuilder || window.MozBlobBuilder;
	      if (Builder) {
	        var builder = new Builder();
	        builder.append(uBuffer);
	        return builder.getBlob(format);
	      } else {
	        return new window.Blob([uBuffer], {
	          type: format
	        });
	      }
	    }
	  }, {
	    key: 'needLogin',
	    value: function needLogin() {
	      return this.getCookie('token') == null;
	    }
	  }, {
	    key: 'changeClass',
	    value: function changeClass(ele, className) {
	      var parentDom = ele.parentNode;
	      var curHasClassDom = parentDom.getElementsByClassName(className)[0];
	      curHasClassDom.classList.remove(className);
	      ele.classList.add(className);
	    }
	  }]);
	  return utils;
	}();

	exports.default = new utils();

/***/ },
/* 88 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	exports.__esModule = true;

	var _iterator = __webpack_require__(89);

	var _iterator2 = _interopRequireDefault(_iterator);

	var _symbol = __webpack_require__(92);

	var _symbol2 = _interopRequireDefault(_symbol);

	var _typeof = typeof _symbol2.default === "function" && typeof _iterator2.default === "symbol" ? function (obj) { return typeof obj; } : function (obj) { return obj && typeof _symbol2.default === "function" && obj.constructor === _symbol2.default && obj !== _symbol2.default.prototype ? "symbol" : typeof obj; };

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = typeof _symbol2.default === "function" && _typeof(_iterator2.default) === "symbol" ? function (obj) {
	  return typeof obj === "undefined" ? "undefined" : _typeof(obj);
	} : function (obj) {
	  return obj && typeof _symbol2.default === "function" && obj.constructor === _symbol2.default && obj !== _symbol2.default.prototype ? "symbol" : typeof obj === "undefined" ? "undefined" : _typeof(obj);
	};

/***/ },
/* 89 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(90), __esModule: true };

/***/ },
/* 90 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(42);
	__webpack_require__(55);
	module.exports = __webpack_require__(91).f('iterator');

/***/ },
/* 91 */
/***/ function(module, exports, __webpack_require__) {

	exports.f = __webpack_require__(53);

/***/ },
/* 92 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(93), __esModule: true };

/***/ },
/* 93 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(94);
	__webpack_require__(41);
	__webpack_require__(103);
	__webpack_require__(104);
	module.exports = __webpack_require__(7).Symbol;

/***/ },
/* 94 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';
	// ECMAScript 6 symbols shim
	var global         = __webpack_require__(6)
	  , has            = __webpack_require__(23)
	  , DESCRIPTORS    = __webpack_require__(15)
	  , $export        = __webpack_require__(5)
	  , redefine       = __webpack_require__(46)
	  , META           = __webpack_require__(95).KEY
	  , $fails         = __webpack_require__(16)
	  , shared         = __webpack_require__(33)
	  , setToStringTag = __webpack_require__(52)
	  , uid            = __webpack_require__(34)
	  , wks            = __webpack_require__(53)
	  , wksExt         = __webpack_require__(91)
	  , wksDefine      = __webpack_require__(96)
	  , keyOf          = __webpack_require__(97)
	  , enumKeys       = __webpack_require__(98)
	  , isArray        = __webpack_require__(99)
	  , anObject       = __webpack_require__(12)
	  , toIObject      = __webpack_require__(24)
	  , toPrimitive    = __webpack_require__(18)
	  , createDesc     = __webpack_require__(19)
	  , _create        = __webpack_require__(49)
	  , gOPNExt        = __webpack_require__(100)
	  , $GOPD          = __webpack_require__(102)
	  , $DP            = __webpack_require__(11)
	  , $keys          = __webpack_require__(21)
	  , gOPD           = $GOPD.f
	  , dP             = $DP.f
	  , gOPN           = gOPNExt.f
	  , $Symbol        = global.Symbol
	  , $JSON          = global.JSON
	  , _stringify     = $JSON && $JSON.stringify
	  , PROTOTYPE      = 'prototype'
	  , HIDDEN         = wks('_hidden')
	  , TO_PRIMITIVE   = wks('toPrimitive')
	  , isEnum         = {}.propertyIsEnumerable
	  , SymbolRegistry = shared('symbol-registry')
	  , AllSymbols     = shared('symbols')
	  , OPSymbols      = shared('op-symbols')
	  , ObjectProto    = Object[PROTOTYPE]
	  , USE_NATIVE     = typeof $Symbol == 'function'
	  , QObject        = global.QObject;
	// Don't use setters in Qt Script, https://github.com/zloirock/core-js/issues/173
	var setter = !QObject || !QObject[PROTOTYPE] || !QObject[PROTOTYPE].findChild;

	// fallback for old Android, https://code.google.com/p/v8/issues/detail?id=687
	var setSymbolDesc = DESCRIPTORS && $fails(function(){
	  return _create(dP({}, 'a', {
	    get: function(){ return dP(this, 'a', {value: 7}).a; }
	  })).a != 7;
	}) ? function(it, key, D){
	  var protoDesc = gOPD(ObjectProto, key);
	  if(protoDesc)delete ObjectProto[key];
	  dP(it, key, D);
	  if(protoDesc && it !== ObjectProto)dP(ObjectProto, key, protoDesc);
	} : dP;

	var wrap = function(tag){
	  var sym = AllSymbols[tag] = _create($Symbol[PROTOTYPE]);
	  sym._k = tag;
	  return sym;
	};

	var isSymbol = USE_NATIVE && typeof $Symbol.iterator == 'symbol' ? function(it){
	  return typeof it == 'symbol';
	} : function(it){
	  return it instanceof $Symbol;
	};

	var $defineProperty = function defineProperty(it, key, D){
	  if(it === ObjectProto)$defineProperty(OPSymbols, key, D);
	  anObject(it);
	  key = toPrimitive(key, true);
	  anObject(D);
	  if(has(AllSymbols, key)){
	    if(!D.enumerable){
	      if(!has(it, HIDDEN))dP(it, HIDDEN, createDesc(1, {}));
	      it[HIDDEN][key] = true;
	    } else {
	      if(has(it, HIDDEN) && it[HIDDEN][key])it[HIDDEN][key] = false;
	      D = _create(D, {enumerable: createDesc(0, false)});
	    } return setSymbolDesc(it, key, D);
	  } return dP(it, key, D);
	};
	var $defineProperties = function defineProperties(it, P){
	  anObject(it);
	  var keys = enumKeys(P = toIObject(P))
	    , i    = 0
	    , l = keys.length
	    , key;
	  while(l > i)$defineProperty(it, key = keys[i++], P[key]);
	  return it;
	};
	var $create = function create(it, P){
	  return P === undefined ? _create(it) : $defineProperties(_create(it), P);
	};
	var $propertyIsEnumerable = function propertyIsEnumerable(key){
	  var E = isEnum.call(this, key = toPrimitive(key, true));
	  if(this === ObjectProto && has(AllSymbols, key) && !has(OPSymbols, key))return false;
	  return E || !has(this, key) || !has(AllSymbols, key) || has(this, HIDDEN) && this[HIDDEN][key] ? E : true;
	};
	var $getOwnPropertyDescriptor = function getOwnPropertyDescriptor(it, key){
	  it  = toIObject(it);
	  key = toPrimitive(key, true);
	  if(it === ObjectProto && has(AllSymbols, key) && !has(OPSymbols, key))return;
	  var D = gOPD(it, key);
	  if(D && has(AllSymbols, key) && !(has(it, HIDDEN) && it[HIDDEN][key]))D.enumerable = true;
	  return D;
	};
	var $getOwnPropertyNames = function getOwnPropertyNames(it){
	  var names  = gOPN(toIObject(it))
	    , result = []
	    , i      = 0
	    , key;
	  while(names.length > i){
	    if(!has(AllSymbols, key = names[i++]) && key != HIDDEN && key != META)result.push(key);
	  } return result;
	};
	var $getOwnPropertySymbols = function getOwnPropertySymbols(it){
	  var IS_OP  = it === ObjectProto
	    , names  = gOPN(IS_OP ? OPSymbols : toIObject(it))
	    , result = []
	    , i      = 0
	    , key;
	  while(names.length > i){
	    if(has(AllSymbols, key = names[i++]) && (IS_OP ? has(ObjectProto, key) : true))result.push(AllSymbols[key]);
	  } return result;
	};

	// 19.4.1.1 Symbol([description])
	if(!USE_NATIVE){
	  $Symbol = function Symbol(){
	    if(this instanceof $Symbol)throw TypeError('Symbol is not a constructor!');
	    var tag = uid(arguments.length > 0 ? arguments[0] : undefined);
	    var $set = function(value){
	      if(this === ObjectProto)$set.call(OPSymbols, value);
	      if(has(this, HIDDEN) && has(this[HIDDEN], tag))this[HIDDEN][tag] = false;
	      setSymbolDesc(this, tag, createDesc(1, value));
	    };
	    if(DESCRIPTORS && setter)setSymbolDesc(ObjectProto, tag, {configurable: true, set: $set});
	    return wrap(tag);
	  };
	  redefine($Symbol[PROTOTYPE], 'toString', function toString(){
	    return this._k;
	  });

	  $GOPD.f = $getOwnPropertyDescriptor;
	  $DP.f   = $defineProperty;
	  __webpack_require__(101).f = gOPNExt.f = $getOwnPropertyNames;
	  __webpack_require__(37).f  = $propertyIsEnumerable;
	  __webpack_require__(36).f = $getOwnPropertySymbols;

	  if(DESCRIPTORS && !__webpack_require__(45)){
	    redefine(ObjectProto, 'propertyIsEnumerable', $propertyIsEnumerable, true);
	  }

	  wksExt.f = function(name){
	    return wrap(wks(name));
	  }
	}

	$export($export.G + $export.W + $export.F * !USE_NATIVE, {Symbol: $Symbol});

	for(var symbols = (
	  // 19.4.2.2, 19.4.2.3, 19.4.2.4, 19.4.2.6, 19.4.2.8, 19.4.2.9, 19.4.2.10, 19.4.2.11, 19.4.2.12, 19.4.2.13, 19.4.2.14
	  'hasInstance,isConcatSpreadable,iterator,match,replace,search,species,split,toPrimitive,toStringTag,unscopables'
	).split(','), i = 0; symbols.length > i; )wks(symbols[i++]);

	for(var symbols = $keys(wks.store), i = 0; symbols.length > i; )wksDefine(symbols[i++]);

	$export($export.S + $export.F * !USE_NATIVE, 'Symbol', {
	  // 19.4.2.1 Symbol.for(key)
	  'for': function(key){
	    return has(SymbolRegistry, key += '')
	      ? SymbolRegistry[key]
	      : SymbolRegistry[key] = $Symbol(key);
	  },
	  // 19.4.2.5 Symbol.keyFor(sym)
	  keyFor: function keyFor(key){
	    if(isSymbol(key))return keyOf(SymbolRegistry, key);
	    throw TypeError(key + ' is not a symbol!');
	  },
	  useSetter: function(){ setter = true; },
	  useSimple: function(){ setter = false; }
	});

	$export($export.S + $export.F * !USE_NATIVE, 'Object', {
	  // 19.1.2.2 Object.create(O [, Properties])
	  create: $create,
	  // 19.1.2.4 Object.defineProperty(O, P, Attributes)
	  defineProperty: $defineProperty,
	  // 19.1.2.3 Object.defineProperties(O, Properties)
	  defineProperties: $defineProperties,
	  // 19.1.2.6 Object.getOwnPropertyDescriptor(O, P)
	  getOwnPropertyDescriptor: $getOwnPropertyDescriptor,
	  // 19.1.2.7 Object.getOwnPropertyNames(O)
	  getOwnPropertyNames: $getOwnPropertyNames,
	  // 19.1.2.8 Object.getOwnPropertySymbols(O)
	  getOwnPropertySymbols: $getOwnPropertySymbols
	});

	// 24.3.2 JSON.stringify(value [, replacer [, space]])
	$JSON && $export($export.S + $export.F * (!USE_NATIVE || $fails(function(){
	  var S = $Symbol();
	  // MS Edge converts symbol values to JSON as {}
	  // WebKit converts symbol values to JSON as null
	  // V8 throws on boxed symbols
	  return _stringify([S]) != '[null]' || _stringify({a: S}) != '{}' || _stringify(Object(S)) != '{}';
	})), 'JSON', {
	  stringify: function stringify(it){
	    if(it === undefined || isSymbol(it))return; // IE8 returns string on undefined
	    var args = [it]
	      , i    = 1
	      , replacer, $replacer;
	    while(arguments.length > i)args.push(arguments[i++]);
	    replacer = args[1];
	    if(typeof replacer == 'function')$replacer = replacer;
	    if($replacer || !isArray(replacer))replacer = function(key, value){
	      if($replacer)value = $replacer.call(this, key, value);
	      if(!isSymbol(value))return value;
	    };
	    args[1] = replacer;
	    return _stringify.apply($JSON, args);
	  }
	});

	// 19.4.3.4 Symbol.prototype[@@toPrimitive](hint)
	$Symbol[PROTOTYPE][TO_PRIMITIVE] || __webpack_require__(10)($Symbol[PROTOTYPE], TO_PRIMITIVE, $Symbol[PROTOTYPE].valueOf);
	// 19.4.3.5 Symbol.prototype[@@toStringTag]
	setToStringTag($Symbol, 'Symbol');
	// 20.2.1.9 Math[@@toStringTag]
	setToStringTag(Math, 'Math', true);
	// 24.3.3 JSON[@@toStringTag]
	setToStringTag(global.JSON, 'JSON', true);

/***/ },
/* 95 */
/***/ function(module, exports, __webpack_require__) {

	var META     = __webpack_require__(34)('meta')
	  , isObject = __webpack_require__(13)
	  , has      = __webpack_require__(23)
	  , setDesc  = __webpack_require__(11).f
	  , id       = 0;
	var isExtensible = Object.isExtensible || function(){
	  return true;
	};
	var FREEZE = !__webpack_require__(16)(function(){
	  return isExtensible(Object.preventExtensions({}));
	});
	var setMeta = function(it){
	  setDesc(it, META, {value: {
	    i: 'O' + ++id, // object ID
	    w: {}          // weak collections IDs
	  }});
	};
	var fastKey = function(it, create){
	  // return primitive with prefix
	  if(!isObject(it))return typeof it == 'symbol' ? it : (typeof it == 'string' ? 'S' : 'P') + it;
	  if(!has(it, META)){
	    // can't set metadata to uncaught frozen object
	    if(!isExtensible(it))return 'F';
	    // not necessary to add metadata
	    if(!create)return 'E';
	    // add missing metadata
	    setMeta(it);
	  // return object ID
	  } return it[META].i;
	};
	var getWeak = function(it, create){
	  if(!has(it, META)){
	    // can't set metadata to uncaught frozen object
	    if(!isExtensible(it))return true;
	    // not necessary to add metadata
	    if(!create)return false;
	    // add missing metadata
	    setMeta(it);
	  // return hash weak collections IDs
	  } return it[META].w;
	};
	// add metadata on freeze-family methods calling
	var onFreeze = function(it){
	  if(FREEZE && meta.NEED && isExtensible(it) && !has(it, META))setMeta(it);
	  return it;
	};
	var meta = module.exports = {
	  KEY:      META,
	  NEED:     false,
	  fastKey:  fastKey,
	  getWeak:  getWeak,
	  onFreeze: onFreeze
	};

/***/ },
/* 96 */
/***/ function(module, exports, __webpack_require__) {

	var global         = __webpack_require__(6)
	  , core           = __webpack_require__(7)
	  , LIBRARY        = __webpack_require__(45)
	  , wksExt         = __webpack_require__(91)
	  , defineProperty = __webpack_require__(11).f;
	module.exports = function(name){
	  var $Symbol = core.Symbol || (core.Symbol = LIBRARY ? {} : global.Symbol || {});
	  if(name.charAt(0) != '_' && !(name in $Symbol))defineProperty($Symbol, name, {value: wksExt.f(name)});
	};

/***/ },
/* 97 */
/***/ function(module, exports, __webpack_require__) {

	var getKeys   = __webpack_require__(21)
	  , toIObject = __webpack_require__(24);
	module.exports = function(object, el){
	  var O      = toIObject(object)
	    , keys   = getKeys(O)
	    , length = keys.length
	    , index  = 0
	    , key;
	  while(length > index)if(O[key = keys[index++]] === el)return key;
	};

/***/ },
/* 98 */
/***/ function(module, exports, __webpack_require__) {

	// all enumerable object keys, includes symbols
	var getKeys = __webpack_require__(21)
	  , gOPS    = __webpack_require__(36)
	  , pIE     = __webpack_require__(37);
	module.exports = function(it){
	  var result     = getKeys(it)
	    , getSymbols = gOPS.f;
	  if(getSymbols){
	    var symbols = getSymbols(it)
	      , isEnum  = pIE.f
	      , i       = 0
	      , key;
	    while(symbols.length > i)if(isEnum.call(it, key = symbols[i++]))result.push(key);
	  } return result;
	};

/***/ },
/* 99 */
/***/ function(module, exports, __webpack_require__) {

	// 7.2.2 IsArray(argument)
	var cof = __webpack_require__(26);
	module.exports = Array.isArray || function isArray(arg){
	  return cof(arg) == 'Array';
	};

/***/ },
/* 100 */
/***/ function(module, exports, __webpack_require__) {

	// fallback for IE11 buggy Object.getOwnPropertyNames with iframe and window
	var toIObject = __webpack_require__(24)
	  , gOPN      = __webpack_require__(101).f
	  , toString  = {}.toString;

	var windowNames = typeof window == 'object' && window && Object.getOwnPropertyNames
	  ? Object.getOwnPropertyNames(window) : [];

	var getWindowNames = function(it){
	  try {
	    return gOPN(it);
	  } catch(e){
	    return windowNames.slice();
	  }
	};

	module.exports.f = function getOwnPropertyNames(it){
	  return windowNames && toString.call(it) == '[object Window]' ? getWindowNames(it) : gOPN(toIObject(it));
	};


/***/ },
/* 101 */
/***/ function(module, exports, __webpack_require__) {

	// 19.1.2.7 / 15.2.3.4 Object.getOwnPropertyNames(O)
	var $keys      = __webpack_require__(22)
	  , hiddenKeys = __webpack_require__(35).concat('length', 'prototype');

	exports.f = Object.getOwnPropertyNames || function getOwnPropertyNames(O){
	  return $keys(O, hiddenKeys);
	};

/***/ },
/* 102 */
/***/ function(module, exports, __webpack_require__) {

	var pIE            = __webpack_require__(37)
	  , createDesc     = __webpack_require__(19)
	  , toIObject      = __webpack_require__(24)
	  , toPrimitive    = __webpack_require__(18)
	  , has            = __webpack_require__(23)
	  , IE8_DOM_DEFINE = __webpack_require__(14)
	  , gOPD           = Object.getOwnPropertyDescriptor;

	exports.f = __webpack_require__(15) ? gOPD : function getOwnPropertyDescriptor(O, P){
	  O = toIObject(O);
	  P = toPrimitive(P, true);
	  if(IE8_DOM_DEFINE)try {
	    return gOPD(O, P);
	  } catch(e){ /* empty */ }
	  if(has(O, P))return createDesc(!pIE.f.call(O, P), O[P]);
	};

/***/ },
/* 103 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(96)('asyncIterator');

/***/ },
/* 104 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(96)('observable');

/***/ },
/* 105 */
/***/ function(module, exports) {

	"use strict";

	exports.__esModule = true;

	exports.default = function (instance, Constructor) {
	  if (!(instance instanceof Constructor)) {
	    throw new TypeError("Cannot call a class as a function");
	  }
	};

/***/ },
/* 106 */
/***/ function(module, exports, __webpack_require__) {

	"use strict";

	exports.__esModule = true;

	var _defineProperty = __webpack_require__(84);

	var _defineProperty2 = _interopRequireDefault(_defineProperty);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = function () {
	  function defineProperties(target, props) {
	    for (var i = 0; i < props.length; i++) {
	      var descriptor = props[i];
	      descriptor.enumerable = descriptor.enumerable || false;
	      descriptor.configurable = true;
	      if ("value" in descriptor) descriptor.writable = true;
	      (0, _defineProperty2.default)(target, descriptor.key, descriptor);
	    }
	  }

	  return function (Constructor, protoProps, staticProps) {
	    if (protoProps) defineProperties(Constructor.prototype, protoProps);
	    if (staticProps) defineProperties(Constructor, staticProps);
	    return Constructor;
	  };
	}();

/***/ },
/* 107 */
/***/ function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	var regExp = {
	  name: /^[a-zA-Z0-9]{1,20}|[u4e00-u9fa5]{1,10}$/,
	  realName: /^[\u4e00-\u9fa5a-zA-Z]{2,8}$/,
	  companyName: /^[\u4e00-\u9fa5a-zA-Z]{2,20}$/,
	  email: /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,5}$/,
	  emailLimit: /^[\@A-Za-z0-9\.\-\_]{46,}$/,
	  QQNum: /^\d{5,14}$/,
	  password: /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\-\_\~\(\)]{6,20}$/,
	  pwdIllegal: /[^\@A-Za-z0-9\!\#\$\%\^\&\*\.\-\_\~\(\)]/,
	  usernameIllegal: /[^\@A-Za-z0-9\!\#\$\%\^\&\*\.\-\_\~\(\)]/,
	  username: /^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\-\_\~\(\)]{1,45}$/,
	  mobile: /^1[34578][0-9]{9}$/,
	  indroution: /^[a-zA-Z][\u4e00-\u9fa5]{1,140}$/,
	  IDcard: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
	  money: /^[1-9][0-9]*$/,
	  userInfoIllegal: /[^\@A-Za-z0-9\.\-\_\u4e00-\u9fa5]/,
	  realNameIllegal: /[^A-Za-z\·\u4e00-\u9fa5]/,
	  mobileIllegal: /[^\d]/
	};

	exports.default = regExp;

/***/ },
/* 108 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(109)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(111),
	  /* template */
	  __webpack_require__(112),
	  /* scopeId */
	  "data-v-55ec4343",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 109 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(110);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("7750f53b", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-55ec4343&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./forgetPassword.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-55ec4343&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./forgetPassword.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 110 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, "input[data-v-55ec4343]:-webkit-autofill{background-color:#fff!important;background-image:none;color:#000}input[placeholder][data-v-55ec4343]{font-size:12px}.form-half-left[data-v-55ec4343]{width:50%;text-align:left;float:left}.logpo[data-v-55ec4343]{text-align:center;color:#18ccc0;font-size:40px}.form-half-right[data-v-55ec4343]{width:50%;text-align:right;float:left}.divide-line[data-v-55ec4343]{border:1px solid #ececec;width:419px;height:0;margin-top:13px}.form-header[data-v-55ec4343]{font-family:MicrosoftYaHei;font-size:18px;color:#4a4a4a;text-align:left}.form-hint[data-v-55ec4343]{font-size:12px;color:#f67171;text-align:right}.form-notice[data-v-55ec4343]{font-family:MicrosoftYaHei;font-size:16px;color:#83817b;text-align:center;line-height:2;margin:20px}.form-mask[data-v-55ec4343]{position:fixed;z-index:102;top:0;left:0;width:100%;height:100%;background-color:rgba(245,247,249,.85);display:table;transition:opacity .3s ease}.forget-wrapper[data-v-55ec4343]{display:table-cell;vertical-align:middle}.form-container[data-v-55ec4343]{position:relative;width:420px;margin:0 auto;padding:30px 40px;background-color:#fff;border-radius:3px;box-shadow:0 2px 8px rgba(0,0,0,.15);transition:all .3s ease;font-family:Helvetica,Arial,sans-serif}.form-container a[data-v-55ec4343]{text-decoration:none;color:inherit}.forget-title[data-v-55ec4343]{width:50%;height:36px;font-family:MicrosoftYaHei;font-size:18px;color:#a6a6a6;line-height:24px;text-align:center;border-bottom:1px solid #ececec;cursor:pointer;font-weight:600}.forget-title.forget-on[data-v-55ec4343]{color:#4a4a4a;border-bottom:2px solid #18ccc0}.forget-form[data-v-55ec4343]{position:relative;margin-top:18px}.forget-form .form-label[data-v-55ec4343]{font-family:MicrosoftYaHei;font-size:12px;color:#83817b;line-height:16px}.forget-form .forget-input[data-v-55ec4343]{background:#fff;border:1px solid #ececec;width:100%;height:35px;margin-top:6px;font-size:18px;color:#4a4a4a}.forget-form .forget-fromwran[data-v-55ec4343]{position:absolute;top:23px;right:5px;font-size:12px;color:#ff596e}.forget-form .forget-fromwran30[data-v-55ec4343]{right:30px!important}.forget-form .forget-fromwran80[data-v-55ec4343]{right:80px!important}.forget-form .forget-fromwran110[data-v-55ec4343]{right:110px!important}.forget-form .forget-fromwran120[data-v-55ec4343]{right:120px!important}.forget-form .forget-tipe[data-v-55ec4343]{position:absolute;top:22px;right:30px;font-size:13px;color:#8e8d8d}.forget-form .forget-tipewran10[data-v-55ec4343]{right:10px!important}.forget-form input[data-v-55ec4343]:focus{outline:none}.form-warning[data-v-55ec4343]{color:#ff566b;font-size:12px;margin-bottom:-15px}.form-button[data-v-55ec4343]{margin-top:30px;background:#18ccc0;border:1px solid #18ccc0;width:100%;height:42px;font-size:15px;color:#fff;line-height:19px;font-weight:800}.form-button[data-v-55ec4343]:hover{background:#14c4b9}.forget-way[data-v-55ec4343]{margin-top:20px;font-size:12px;height:50px}.form-label2[data-v-55ec4343]{color:#b7b7b7;font-size:12px;line-height:20px;font-family:MicrosoftYaHei}.form-label[data-v-55ec4343]{font-size:12px;color:#5398c0;line-height:20px;font-family:MicrosoftYaHei}.forget-clear[data-v-55ec4343]{clear:both}.forget-assist[data-v-55ec4343]{margin-top:15px;margin-bottom:20px;font-size:12px;color:#83817b;line-height:12px;width:420px;height:12px}.to-inner-button[data-v-55ec4343]{cursor:pointer}.forget-password[data-v-55ec4343]{margin-top:0}.login-checkbox[data-v-55ec4343]{width:50%;height:12px;line-height:12px;float:left}.login-checkbox a[data-v-55ec4343]{color:#5398c0}.change-to-email[data-v-55ec4343]{width:50%;height:12px;line-height:12px;text-align:right;float:left;color:#4a4a4a}.modal-close[data-v-55ec4343]{position:absolute;right:12px;top:12px;cursor:pointer}.modal-close img[data-v-55ec4343]{width:14px}.identifyingCode-img[data-v-55ec4343]{position:absolute;right:-2px;top:24px;width:75px;height:35px;cursor:pointer;border:none}.obtain-code[data-v-55ec4343]{position:absolute;right:-2px;top:22px;width:70px;height:14px;margin-right:10px;cursor:pointer;border:1px solid #18ccc0!important;background-color:#18ccc0;margin:auto;padding:11px 23px;color:#fff;font-size:14px;text-align:center}.disable[data-v-55ec4343]{background:#afafaf!important;border:1px solid #ddd!important}.seePsw-img[data-v-55ec4343]{position:absolute;top:34px;right:5px;width:20px;cursor:pointer}.seePsw-img2[data-v-55ec4343],.seePsw-img3[data-v-55ec4343]{display:none}.modal-enter[data-v-55ec4343],.modal-leave[data-v-55ec4343]{opacity:0}.modal-enter .login-container[data-v-55ec4343],.modal-leave .login-container[data-v-55ec4343]{-webkit-transform:scale(.9);transform:scale(.9)}.verify-input[data-v-55ec4343]{width:290px!important}.tipsBar[data-v-55ec4343]{position:fixed;top:0;left:50%;width:500px;margin-left:-250px;z-index:99999}.tipsBar-container[data-v-55ec4343]{box-sizing:border-box;position:relative;line-height:48px;margin:0 auto;background:rgba(0,0,0,.5);height:48px}.tipsBar span[data-v-55ec4343]{padding-left:20px;width:10px;height:10px;margin:32px auto;font-size:16px}.tipsBar span color #FFFFFF .success[data-v-55ec4343]{color:#b3ec11}.tipsBar span color #FFFFFF .fail[data-v-55ec4343]{color:#f67171}", ""]);

	// exports


/***/ },
/* 111 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _regExp = __webpack_require__(107);

	var _regExp2 = _interopRequireDefault(_regExp);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	  data: function data() {
	    return {
	      mobile: '',
	      username: '',
	      password: '',
	      femail: '',
	      verifycode: '',
	      resetToken: '',
	      tips: '',
	      iswarning: {
	        emailWarning: false,
	        passwordWarning: false,
	        mobileWarning: false,
	        verifyCodelWarning: false,
	        newpassword: false,
	        confirmpassword: false,
	        apiError: false,
	        tips: false,
	        femailWarning: false
	      },
	      warnword: 'faillogin',
	      getcode: '获取验证码',
	      verificationerror: 'verificationerror',
	      counting: false,
	      forgetPassword: false,
	      byMobile: true,
	      notVerifed: true,
	      changeNotice: false,
	      checkMail: false,
	      sendbutton: 'sendbutton',
	      resetSuccessTips: ''
	    };
	  },
	  ready: function ready() {},

	  props: ['show'],
	  components: {},
	  watch: {
	    'mobile': function mobile() {
	      if (this.mobile == '' || !_regExp2.default.mobile.test(this.mobile)) {
	        this.iswarning.mobileWarning = true;
	      } else {
	        this.iswarning.mobileWarning = false;
	      }
	    },
	    'verifycode': function verifycode() {
	      this.iswarning.verifyCodelWarning = false;
	      if (this.verfiycode == '') {
	        this.iswarning.verifyCodelWarning = true;
	      }
	    },
	    'newpassword': function newpassword() {
	      if (!_regExp2.default.password.test(this.newpassword)) {
	        this.iswarning.newpassword = true;
	      } else {
	        this.iswarning.newpassword = false;
	      }
	    },
	    'confirmpassword': function confirmpassword() {
	      if (this.confirmpassword != this.newpassword) {
	        this.iswarning.confirmpassword = true;
	      } else {
	        this.iswarning.confirmpassword = false;
	      }
	    }
	  },

	  methods: {
	    showLogin: function showLogin() {
	      this.show.isLogin = true;
	      this.forgetPassword = false;
	      this.clearWarningFun();
	    },
	    closeForget: function closeForget() {
	      this.clearWarningFun();
	      this.show.isForget = false;
	      this.show.isShow = true;
	      this.notVerifed = true;
	      this.byMobile = true;
	      this.changeNotice = false;
	      this.mobile = '';
	      this.femail = '';
	      this.verifycode = '';
	    },
	    clearWarningFun: function clearWarningFun() {
	      this.iswarning.loginWarning = false;
	      this.iswarning.loginPassword = false;
	      this.iswarning.emailWarning = false, this.iswarning.passwordWarning = false, this.iswarning.sigupWarning = false, this.iswarning.verificationWarning = false, this.iswarning.loginUsername = false, this.iswarning.mobileWarning = false, this.iswarning.femailWarning = false;
	      this.iswarning.tips = false;
	    },
	    checkboxChange: function checkboxChange(e) {
	      this.checked = e.target.checked;
	    },
	    resetTimer: function resetTimer(msg) {
	      clearInterval(this.timer);
	      this.getcode = msg;
	      this.counting = false;
	    },

	    // 发送重置密码的短信验证码
	    getVerifyCode: function getVerifyCode() {
	      var _this = this;

	      this.clearWarningFun();
	      //正在读秒的话不能点击
	      if (this.counting === true) {
	        return;
	      }

	      if (!_regExp2.default.mobile.test(this.mobile)) {
	        this.iswarning.mobileWarning = true;
	      } else {
	        (function () {
	          _this.counting = false;
	          _this.getcode = '获取中...';
	          var self = _this;
	          $.ajax({
	            url: _config2.default.API_RESET_VERIFY_CODE,
	            type: 'POST',
	            dataType: 'json',
	            data: {
	              mobile: _this.mobile
	            },
	            success: function success(result) {
	              var msg = result.message;
	              //接口返回一些错误码，不进行读数操作，直接返回
	              if (result.code == 200) {
	                //设置读秒内容，置灰
	                var second = 60;
	                self.counting = true;
	                self.getcode = second + "s后重试";

	                self.timer = setInterval(function () {
	                  if (second == 0) {
	                    self.resetTimer('重新获取');
	                  } else {
	                    second--;
	                    self.getcode = second + "s后重试";
	                  }
	                }, 1000);
	              } else {
	                self.iswarning.tips = true;
	                self.tips = msg;
	                // self.$dispatch('showTipsBar', {type: 'fail', msg: msg})
	                self.resetTimer('重新获取');
	              }
	            },
	            error: function error(_error) {
	              self.iswarning.tips = true;
	              self.tips = '网络错误，请重新获取';
	              // self.$dispatch('showTipsBar', {type: 'fail', msg: '网络错误，请重新获取'})
	              self.resetTimer('重新获取');
	            }
	          });
	        })();
	      }
	    },

	    // 验证验证码是否正确
	    checkVerifyCode: function checkVerifyCode(e) {
	      var self = this;
	      e.preventDefault();
	      if (self.verifycode == '') {
	        this.iswarning.verifyCodelWarning = true;
	        return;
	      }
	      if (!_regExp2.default.mobile.test(this.mobile)) {
	        this.iswarning.mobileWarning = true;
	        return;
	      }
	      $.ajax({
	        url: _config2.default.API_RESET_CHECK_VERIFY_CODE,
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          mobile: this.mobile,
	          code: this.verifycode
	        },
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code === 200) {
	            self.notVerifed = false;
	            self.resetToken = result.data.token;
	          } else {
	            self.iswarning.tips = true;
	            self.tips = msg;
	          }
	          self.sendbutton = 'sendbutton';
	        }
	      });
	    },
	    showForgetPassword: function showForgetPassword() {
	      this.show.isForget = true;
	      this.show.isShow = false;
	    },
	    findByMobile: function findByMobile() {
	      this.byMobile = true;
	    },
	    findByEmail: function findByEmail() {
	      this.byMobile = false;
	      this.notVerifed = true;
	    },
	    goToLogin: function goToLogin() {
	      this.show.isForget = false;
	      this.show.isShow = true;
	      this.notVerifed = true;
	    },
	    resetPassword: function resetPassword(e) {
	      e.preventDefault();
	      this.notVerifed = false;
	      this.changeNotice = false;
	    },
	    sendEmail: function sendEmail(e) {
	      e.preventDefault();
	      var self = this;
	      this.clearWarningFun();
	      var regEmail = _regExp2.default.email;

	      if (!regEmail.test(this.femail)) {
	        console.log('邮箱格式错误');
	        this.warnword = '邮箱格式错误';
	        this.iswarning.femailWarning = true;
	        return;
	      }

	      self.sendbutton = '正在发送邮件...';
	      $.ajax({
	        url: _config2.default.API_FINDPWD,
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          email: this.femail
	        },
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code === 200) {
	            console.log('找回密码成功，请登录邮箱查收相关邮件');
	            self.resetSuccessTips = 'sendmail';
	            self.notVerifed = false;
	            self.changeNotice = true;
	          } else {
	            self.warnword = '邮箱不存在,请重新输入';
	            self.iswarning.femailWarning = true;
	          }
	          self.sendbutton = 'sendbutton';
	        }
	      });
	      console.log('sendEnd');
	    },
	    changePassword: function changePassword(e) {
	      e.preventDefault();
	      if (this.newpassword == '') {
	        this.iswarning.newpassword = true;
	        return;
	      }
	      if (!_regExp2.default.password.test(this.newpassword)) {
	        this.iswarning.newpassword = true;
	        return;
	      }
	      if (this.confirmpassword != this.newpassword) {
	        this.iswarning.confirmpassword = true;
	        return;
	      }

	      var self = this;
	      $.ajax({
	        url: _config2.default.API_V4_FINDPWD,
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          mobile: this.mobile,
	          token: this.resetToken,
	          password: this.newpassword
	        },
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code === 200) {
	            // self.$dispatch('showTipsBar', {type: 'success', msg: '密码修改成功！'})
	            self.goToLogin();
	          } else {
	            self.iswarning.tips = true;
	            self.tips = msg;
	            // self.$dispatch('showTipsBar', {type: 'fail', msg: msg})
	          }
	        }
	      });
	    }
	  }
	};

/***/ },
/* 112 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return (_vm.show.isForget) ? _c('div', {
	    staticClass: "form-mask",
	    attrs: {
	      "transition": "modal"
	    }
	  }, [(_vm.notVerifed) ? _c('div', {
	    staticClass: "forget-wrapper"
	  }, [_c('div', {
	    staticClass: "form-container"
	  }, [_c('div', {
	    staticClass: "form-header"
	  }, [_vm._v("找回密码")]), _vm._v(" "), _c('div', {
	    staticClass: "divide-line"
	  }), _vm._v(" "), (_vm.byMobile) ? _c('div', [_c('form', [_c('div', {
	    staticClass: "forget-form"
	  }, [_c('div', {
	    staticClass: "form-label",
	    attrs: {
	      "for": "mobile"
	    }
	  }, [_vm._v("手机号")]), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.mobile),
	      expression: "mobile"
	    }],
	    staticClass: "forget-input",
	    attrs: {
	      "type": "text",
	      "name": "mobile"
	    },
	    domProps: {
	      "value": _vm._s(_vm.mobile)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.mobile = $event.target.value
	      }
	    }
	  }), _vm._v(" "), (_vm.iswarning.mobileWarning) ? _c('p', {
	    staticClass: "forget-fromwran"
	  }, [_vm._v("请输入正确的手机号")]) : _vm._e()]), _vm._v(" "), _c('div', {
	    staticClass: "forget-form"
	  }, [_c('div', {
	    staticClass: "form-label",
	    attrs: {
	      "for": "verifycode"
	    }
	  }, [_vm._v("验证码")]), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.verifycode),
	      expression: "verifycode"
	    }],
	    staticClass: "forget-input verify-input",
	    attrs: {
	      "type": "text",
	      "name": "verifycode"
	    },
	    domProps: {
	      "value": _vm._s(_vm.verifycode)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.verifycode = $event.target.value
	      }
	    }
	  }), _vm._v(" "), (_vm.iswarning.verifyCodelWarning) ? _c('p', {
	    staticClass: "forget-fromwran forget-fromwran125"
	  }, [_vm._v("请输入正确的验证码")]) : _vm._e(), _vm._v(" "), _c('p', {
	    staticClass: "obtain-code",
	    class: {
	      'disable': _vm.counting
	    },
	    on: {
	      "click": _vm.getVerifyCode
	    }
	  }, [_vm._v(_vm._s(_vm.getcode))])]), _vm._v(" "), (_vm.iswarning.tips) ? _c('p', {
	    staticClass: "form-warning"
	  }, [_vm._v(_vm._s(_vm.tips))]) : _vm._e(), _vm._v(" "), _c('button', {
	    staticClass: "form-button",
	    attrs: {
	      "type": "submit"
	    },
	    on: {
	      "click": _vm.checkVerifyCode
	    }
	  }, [_vm._v("重置密码")])]), _vm._v(" "), _c('div', {
	    staticClass: "forget-assist"
	  }, [_c('div', {
	    staticClass: "form-label form-half-left"
	  }, [_c('span', {
	    staticClass: "form-label2"
	  }, [_vm._v("有账号,")]), _c('span', {
	    staticClass: "to-inner-button forget-password",
	    on: {
	      "click": _vm.closeForget
	    }
	  }, [_vm._v("去登录")])]), _vm._v(" "), _c('div', {
	    staticClass: "form-label form-half-right"
	  }, [_c('span', {
	    staticClass: "to-inner-button forget-password",
	    on: {
	      "click": _vm.findByEmail
	    }
	  }, [_vm._v("使用邮箱找回密码")])])])]) : _c('div', [_c('form', [_c('div', {
	    staticClass: "forget-form"
	  }, [_c('div', {
	    staticClass: "form-label",
	    attrs: {
	      "for": "femail"
	    }
	  }, [_vm._v("邮箱")]), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.femail),
	      expression: "femail"
	    }],
	    staticClass: "forget-input",
	    attrs: {
	      "type": "text",
	      "name": "femail"
	    },
	    domProps: {
	      "value": _vm._s(_vm.femail)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.femail = $event.target.value
	      }
	    }
	  }), _vm._v(" "), (_vm.iswarning.loginUsername) ? _c('p', {
	    staticClass: "forget-fromwran"
	  }, [_vm._v("验证失败")]) : _vm._e()]), _vm._v(" "), (_vm.iswarning.femailWarning) ? _c('p', {
	    staticClass: "form-warning"
	  }, [_vm._v(_vm._s(_vm.warnword))]) : _vm._e(), _vm._v(" "), _c('button', {
	    staticClass: "form-button",
	    attrs: {
	      "type": "submit"
	    },
	    on: {
	      "click": _vm.sendEmail
	    }
	  }, [_vm._v("发送邮件")])]), _vm._v(" "), _c('div', {
	    staticClass: "forget-assist"
	  }, [_c('div', {
	    staticClass: "form-label form-half-left"
	  }, [_c('span', {
	    staticClass: "form-label2"
	  }, [_vm._v("有账号,")]), _c('span', {
	    staticClass: "to-inner-button forget-password",
	    on: {
	      "click": _vm.closeForget
	    }
	  }, [_vm._v("去登录")])]), _vm._v(" "), _c('div', {
	    staticClass: "form-label form-half-right"
	  }, [_c('span', {
	    staticClass: "to-inner-button forget-password",
	    on: {
	      "click": _vm.findByMobile
	    }
	  }, [_vm._v("使用手机找回")])])])]), _vm._v(" "), _c('div', {
	    staticClass: "modal-close",
	    on: {
	      "click": _vm.closeForget
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })])])]) : _c('div', {
	    staticClass: "forget-wrapper"
	  }, [(_vm.changeNotice) ? _c('div', [_c('div', {
	    staticClass: "form-container"
	  }, [_c('div', {
	    staticClass: "logpo"
	  }, [_vm._v("MAKA")]), _vm._v(" "), _c('div', {
	    staticClass: "divide-line"
	  }), _vm._v(" "), _c('div', {
	    staticClass: "form-notice"
	  }, [_c('p', [_vm._v("【MAKA.im平台】找回密码邮件已经发送到您的邮箱。|请查收并点击邮件内链接。")])]), _vm._v(" "), _c('button', {
	    staticClass: "form-button",
	    attrs: {
	      "type": "submit"
	    },
	    on: {
	      "click": _vm.goToLogin
	    }
	  }, [_vm._v("立即登录")]), _vm._v(" "), _c('div', {
	    staticClass: "modal-close",
	    on: {
	      "click": _vm.closeForget
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })])])]) : _c('div', [_c('div', {
	    staticClass: "form-container"
	  }, [_c('div', {
	    staticClass: "form-header"
	  }, [_vm._v("找回密码")]), _vm._v(" "), _c('div', {
	    staticClass: "divide-line"
	  }), _vm._v(" "), _c('form', [_c('div', {
	    staticClass: "forget-form"
	  }, [_c('div', {
	    staticClass: "form-label",
	    attrs: {
	      "for": "newpassword"
	    }
	  }, [_vm._v("新密码")]), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.newpassword),
	      expression: "newpassword"
	    }],
	    staticClass: "forget-input",
	    attrs: {
	      "type": "text",
	      "name": "newpassword"
	    },
	    domProps: {
	      "value": _vm._s(_vm.newpassword)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.newpassword = $event.target.value
	      }
	    }
	  }), _vm._v(" "), (_vm.iswarning.newpassword) ? _c('p', {
	    staticClass: "forget-fromwran"
	  }, [_vm._v("密码不符合规范，请重新输入")]) : _vm._e()]), _vm._v(" "), _c('div', {
	    staticClass: "forget-form"
	  }, [_c('div', {
	    staticClass: "form-label",
	    attrs: {
	      "for": "confirmpassword"
	    }
	  }, [_vm._v("确认密码")]), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.confirmpassword),
	      expression: "confirmpassword"
	    }],
	    staticClass: "forget-input",
	    attrs: {
	      "type": "text",
	      "name": "confirmpassword"
	    },
	    domProps: {
	      "value": _vm._s(_vm.confirmpassword)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.confirmpassword = $event.target.value
	      }
	    }
	  }), _vm._v(" "), (_vm.iswarning.confirmpassword) ? _c('p', {
	    staticClass: "forget-fromwran"
	  }, [_vm._v("两次密码输入不一致")]) : _vm._e()]), _vm._v(" "), _c('button', {
	    staticClass: "form-button",
	    attrs: {
	      "type": "submit"
	    },
	    on: {
	      "click": _vm.changePassword
	    }
	  }, [_vm._v("确认修改")]), _vm._v(" "), _c('div', {
	    staticClass: "modal-close",
	    on: {
	      "click": _vm.closeForget
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })])])])])])]) : _vm._e()
	},staticRenderFns: []}

/***/ },
/* 113 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(114)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(116),
	  /* template */
	  __webpack_require__(117),
	  /* scopeId */
	  "data-v-abf8f3d0",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 114 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(115);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("00a6c5d4", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-abf8f3d0&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newIdentifyModel.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-abf8f3d0&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newIdentifyModel.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 115 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".newIdentify[data-v-abf8f3d0]{position:fixed;background:hsla(0,0%,100%,.6);top:0;bottom:0;left:0;right:0;z-index:999}.model[data-v-abf8f3d0]{width:402px;height:424px;position:absolute;background:#fff;box-shadow:0 2px 10px 0 rgba(0,0,0,.8);border-radius:6px;left:0;right:0;top:271px;margin:auto}.model .top[data-v-abf8f3d0]{width:100%;height:71px;position:relative;border-radius:6px 6px 0 0;text-align:center}.model .top span[data-v-abf8f3d0]{font-size:24px;color:#4a4a4a;line-height:71px}.model .top .closeBtn[data-v-abf8f3d0]{position:absolute;width:24px;height:24px;background:#fff;border:1px solid #a2a2a2;border-radius:100%;color:#a2a2a2;line-height:24px;font-size:10px;right:9px;top:9px;margin:auto;cursor:pointer;text-align:center;box-sizing:border-box}.model .top .closeBtn[data-v-abf8f3d0]:hover{color:#fff;border:1px solid #ff7272;background:#ff7272}.model .top .closeBtn i[data-v-abf8f3d0]{padding-left:1.5px;line-height:inherit}.model .selectArea[data-v-abf8f3d0]{width:100%;height:270px;padding:0 26px;box-sizing:border-box}.model .selectArea .bussinessArea[data-v-abf8f3d0],.model .selectArea .personalArea[data-v-abf8f3d0]{width:160px;height:270px;float:left;background:#fff;border:1px solid #fff;border-top:4px solid #fff;border-radius:0 0 6px 6px;box-sizing:border-box;cursor:pointer}.model .selectArea .active[data-v-abf8f3d0]{border:1px solid #18ccc0;border-top:4px solid #18ccc0;box-shadow:0 0 3px 0 rgba(83,255,243,.5)}.model .selectArea .bussinessArea[data-v-abf8f3d0]{float:right}.model .selectArea .bussinessImg[data-v-abf8f3d0],.model .selectArea .personalImg[data-v-abf8f3d0]{margin-top:17px;text-align:center;width:100%;height:126px}.model .selectArea .bussinessImg img[data-v-abf8f3d0],.model .selectArea .personalImg img[data-v-abf8f3d0]{width:126px;height:126px}.model .selectArea .selectTitle[data-v-abf8f3d0]{font-size:18px;color:#4a4a4a;text-align:center;margin-top:10px}.model .selectArea .selectDec[data-v-abf8f3d0]{font-size:12px;color:#4a4a4a;line-height:24px;text-align:center;padding:0 15px;margin-top:5px}.model .regBtn[data-v-abf8f3d0]{position:relative;width:100%;height:44px;margin-top:15px}.model .regBtn .btn[data-v-abf8f3d0]{position:absolute;width:350px;height:42px;background:#18ccc0;border:1px solid #18ccc0;border-radius:2px;left:0;right:0;top:0;bottom:0;margin:auto;font-size:14px;color:#fff;line-height:42px;text-align:center;cursor:pointer}", ""]);

	// exports


/***/ },
/* 116 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	  props: ['show'],
	  data: function data() {
	    return {
	      regType: 'personal'
	    };
	  },
	  mounted: function mounted() {
	    // 初次登录需要认证个人
	    var isFirst = _utils2.default.getCookie('isFirst');
	    var hash = _utils2.default.getRequest();

	    if (isFirst === '1' || hash['register'] === '1') {
	      this.show.showIdentifyModel = true;
	    }
	  },

	  components: {},
	  computed: {},
	  events: {},
	  methods: {
	    select: function select(type) {
	      var self = this;
	      self.regType = type;
	    },
	    toReg: function toReg() {
	      var self = this;
	      _utils2.default.deleteCookie('isFirst');
	      if (self.regType == 'personal') {
	        self.show.showIdentifyModel = false;
	        // location.reload()
	        location.href = window.location.pathname;
	      } else {
	        self.show.showIdentifyModel = false;
	        self.show.primaryIdentify = true;
	        $.ajax({
	          url: _config2.default.API_V4_BUSINESS_SET_BUSINESS_IDENTITY,
	          type: 'PUT',
	          dataType: 'json',
	          data: {},
	          success: function success(result) {
	            console.log('企业认证');
	          },
	          error: function error(_error) {
	            console.log(_error);
	          }
	        });
	      }
	    },
	    successAll: function successAll() {
	      var self = this;
	      _utils2.default.deleteCookie('isFirst');
	      this.$emit('closeAll', true);
	      setTimeout(function () {
	        // location.reload()
	        location.href = window.location.pathname;
	      }, 1000);
	    }
	  }
	};

/***/ },
/* 117 */
/***/ function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    directives: [{
	      name: "show",
	      rawName: "v-show",
	      value: (_vm.show.showIdentifyModel),
	      expression: "show.showIdentifyModel"
	    }],
	    staticClass: "newIdentify"
	  }, [_c('div', {
	    staticClass: "model"
	  }, [_c('div', {
	    staticClass: "top"
	  }, [_c('span', [_vm._v("请选择身份，完成注册")]), _c('div', {
	    staticClass: "closeBtn",
	    on: {
	      "click": _vm.successAll
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })])]), _c('div', {
	    staticClass: "selectArea"
	  }, [_c('div', {
	    staticClass: "personalArea",
	    class: _vm.regType == "personal" ? "active" : "",
	    on: {
	      "click": function($event) {
	        _vm.select("personal")
	      }
	    }
	  }, [_c('div', {
	    staticClass: "personalImg"
	  }, [(_vm.regType == "personal") ? _c('img', {
	    attrs: {
	      "src": __webpack_require__(118)
	    }
	  }) : _c('img', {
	    attrs: {
	      "src": __webpack_require__(119)
	    }
	  })]), _c('div', {
	    staticClass: "selectTitle"
	  }, [_vm._v("个人用户")]), _c('div', {
	    staticClass: "selectDec"
	  }, [_vm._v("提供各项基本功能，可快速完成注册")])]), _c('div', {
	    staticClass: "bussinessArea",
	    class: _vm.regType == "bussiness" ? "active" : "",
	    on: {
	      "click": function($event) {
	        _vm.select("bussiness")
	      }
	    }
	  }, [_c('div', {
	    staticClass: "bussinessImg"
	  }, [(_vm.regType == "bussiness") ? _c('img', {
	    attrs: {
	      "src": __webpack_require__(120)
	    }
	  }) : _c('img', {
	    attrs: {
	      "src": __webpack_require__(121)
	    }
	  })]), _c('div', {
	    staticClass: "selectTitle"
	  }, [_vm._v("企业用户")]), _c('div', {
	    staticClass: "selectDec"
	  }, [_vm._v("完成企业认证，可获得高级权限与体验资格可获得MAKA……服务")])])]), _c('div', {
	    staticClass: "regBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    on: {
	      "click": _vm.toReg
	    }
	  }, [_vm._v(_vm._s(_vm.regType == 'personal' ? '完成注册' : '完成注册，前往认证'))])])])])
	},staticRenderFns: []}

/***/ },
/* 118 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "4f3f635e8b526096372b4d976e854b9f.png";

/***/ },
/* 119 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "c2433ae77d1f20ce4e8fc5fea16e02c9.png";

/***/ },
/* 120 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "96a43d7ed96fed7e9af0c95e2c733828.png";

/***/ },
/* 121 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "3f6539a336fead1db6119b28842030d8.png";

/***/ },
/* 122 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(123)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(125),
	  /* template */
	  __webpack_require__(126),
	  /* scopeId */
	  "data-v-b9614f14",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 123 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(124);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("8ca6695a", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-b9614f14&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./primaryIdentifyModel.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-b9614f14&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./primaryIdentifyModel.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 124 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".primaryIdentifyModel[data-v-b9614f14]{position:absolute;background:hsla(0,0%,100%,.6);top:0;bottom:0;left:0;right:0;z-index:999}.model[data-v-b9614f14]{width:402px;height:750px;position:absolute;background:#fff;box-shadow:0 2px 10px 0 rgba(0,0,0,.3);border-radius:6px;left:0;right:0;top:80px;margin:auto}.model .top[data-v-b9614f14]{width:100%;height:71px;position:relative;border-radius:6px 6px 0 0;text-align:center}.model .top span[data-v-b9614f14]{font-size:24px;color:#4a4a4a;line-height:71px}.model .top .closeBtn[data-v-b9614f14]{position:absolute;width:24px;height:24px;background:#fff;border:1px solid #a2a2a2;border-radius:100%;color:#a2a2a2;line-height:24px;font-size:10px;right:9px;top:9px;margin:auto;cursor:pointer;text-align:center;box-sizing:border-box}.model .top .closeBtn[data-v-b9614f14]:hover{color:#fff;border:1px solid #ff7272;background:#ff7272}.model .top .closeBtn i[data-v-b9614f14]{line-height:inherit;padding-left:1.5px}.model .topDesc[data-v-b9614f14]{width:100%;height:20px;font-size:14px;color:#4a4a4a;text-align:center}.model .topInfo[data-v-b9614f14]{width:100%;height:71px;padding:0 26px;box-sizing:border-box;margin-top:10px}.model .topInfo-left[data-v-b9614f14],.model .topInfo-right[data-v-b9614f14]{width:165px;height:71px;float:left;background:#fdfdfd;border-radius:3px}.model .topInfo-left img[data-v-b9614f14],.model .topInfo-right img[data-v-b9614f14]{float:left;margin-top:8px;margin-left:10px}.model .topInfo-left span[data-v-b9614f14],.model .topInfo-right span[data-v-b9614f14]{float:left;width:76px;font-size:12px;color:#4a4a4a;line-height:18px;margin-top:18px;margin-left:10px}.model .topInfo-right[data-v-b9614f14]{float:right}.model .middleDesc[data-v-b9614f14]{font-size:12px;color:#4a4a4a;width:350px;margin-left:26px;border-top:1px dashed #18ccc0;padding-top:11px;margin-top:15px}.model .mainTitle[data-v-b9614f14]{width:200px;height:21px;font-size:16px;color:#4a4a4a;padding-left:5px;border-left:3px solid #18ccc0;margin-left:26px;margin-top:20px}.model .mainInput[data-v-b9614f14]{margin-top:7px;width:100%;height:22px}.model .mainInput select[data-v-b9614f14]{background:#fdfdfd;border:1px solid #efefef;border-radius:2px;font-size:12px;color:#4a4a4a;line-height:12px}.model .mainInput select[data-v-b9614f14]:focus,.model .mainInput select[data-v-b9614f14]:hover{border:1px solid #cbcbcb}.model .mainInput select.industry[data-v-b9614f14]{position:absolute;width:350px;left:0;right:0;margin:auto}.model .mainInput select.city[data-v-b9614f14],.model .mainInput select.province[data-v-b9614f14]{width:96px;float:left}.model .mainInput select.province[data-v-b9614f14]{margin-left:26px}.model .mainInput select.city[data-v-b9614f14]{margin-left:10px}.model .companyInput[data-v-b9614f14]{position:relative;width:100%;height:34px;margin-top:10px}.model .inputArea i[data-v-b9614f14]{position:absolute;top:0;bottom:0;margin:auto;line-height:34px;color:#4a4a4a;margin-left:34px;z-index:1}.model .inputArea i.phoneIcon[data-v-b9614f14]{font-size:18px}.model .inputArea i.codeIcon[data-v-b9614f14]{font-size:15px}.model .inputArea i.passwordIcon[data-v-b9614f14]{font-size:12px;margin-left:36px}.model .inputArea i.personIcon[data-v-b9614f14]{font-size:16px}.model .inputArea i.positionIcon[data-v-b9614f14]{margin-left:177px;font-size:16px}.model .inputArea i.emailIcon[data-v-b9614f14]{margin-left:8px;font-size:12px}.model .inputArea input[data-v-b9614f14]{position:absolute;background:#fdfdfd;border-radius:3px;border:1px solid #efefef;width:350px;height:30px;outline:none;padding-left:28px;font-size:12px;color:#000;line-height:34px;left:0;right:0;top:0;margin:auto}.model .inputArea input[data-v-b9614f14]:hover{border:1px solid #cbcbcb}.model .inputArea input[data-v-b9614f14]:focus{border:1px solid #18ccc0;box-shadow:0 0 3px 0 rgba(105,255,244,.8)}.model .inputArea input[data-v-b9614f14]::-webkit-input-placeholder{color:#4a4a4a}.model .inputArea input.error[data-v-b9614f14]{border:1px solid #f67171}.model .inputArea input.error[data-v-b9614f14]::-webkit-input-placeholder{color:#f67171}.model .inputArea .errorTips[data-v-b9614f14]{position:absolute;font-size:12px;color:#f67171;line-height:33px;right:34px}.model .nameInput[data-v-b9614f14]{position:relative;width:100%;height:34px;margin-top:10px}.model .nameInput input#name[data-v-b9614f14]{width:100px;left:26px;right:100%}.model .nameInput input#position[data-v-b9614f14]{width:100px;left:170px;right:100%}.model .emailInput[data-v-b9614f14]{position:relative;width:350px;height:51px;margin-top:10px;margin-left:26px;padding-bottom:16px;border-bottom:1px dashed #979797;box-sizing:border-box}.model .tel[data-v-b9614f14]{position:relative;width:100%;height:34px;margin-top:14px}.model .verifyCode[data-v-b9614f14]{position:relative;width:100%;height:34px;margin-top:12px}.model .verifyCode input[data-v-b9614f14]{left:25px;right:100%;width:190px}.model .verifyCode .verifyCodeBtn[data-v-b9614f14]{position:absolute;width:120px;height:34px;background:#18ccc0;border-radius:2px;font-size:12px;color:#fff;line-height:34px;text-align:center;cursor:pointer;right:26px;top:0;bottom:0;margin:auto}.model .verifyCode .disable[data-v-b9614f14]{background:#ccc}.model .sendErrorTips[data-v-b9614f14]{width:100%;height:22px;font-size:12px;color:#f67171;float:right;line-height:22px;padding-left:26px;box-sizing:border-box}.model .sendBtn[data-v-b9614f14]{position:relative;width:100%;height:44px;margin-top:22px}.model .sendBtn .btn[data-v-b9614f14]{position:absolute;width:350px;height:42px;background:#18ccc0;border:1px solid #18ccc0;border-radius:2px;left:0;right:0;top:0;bottom:0;margin:auto;font-size:14px;color:#fff;line-height:42px;text-align:center;cursor:pointer}.model .cancelBtn[data-v-b9614f14]{position:relative;width:100%;height:44px;margin-top:22px}.model .cancelBtn .btn[data-v-b9614f14]{position:absolute;width:350px;height:42px;background:#fff;border:1px solid #18ccc0;border-radius:2px;left:0;right:0;top:0;bottom:0;margin:auto;font-size:14px;color:#18ccc0;line-height:42px;text-align:center;cursor:pointer}.successModel[data-v-b9614f14]{height:276px;margin-top:200px}.successModel .successDesc[data-v-b9614f14]{font-size:14px;color:#4a4a4a;padding:0 26px;box-sizing:border-box}", ""]);

	// exports


/***/ },
/* 125 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _regExp = __webpack_require__(107);

	var _regExp2 = _interopRequireDefault(_regExp);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	  props: ['show'],
	  data: function data() {
	    return {
	      industries: [],
	      provinceCity: {},
	      industry: 'none',
	      province: 'none',
	      city: 'none',
	      company: '',
	      name: '',
	      email: '',
	      phone: '',
	      position: '',
	      verifyCode: '',
	      getcode: '获取验证码',
	      counting: false,
	      sendErrorTips: '',
	      companyErrorTips: '',
	      emailErrorTips: '',
	      phoneErrorTips: '',
	      verifyCodeErrorTips: '',
	      identifySuccess: false
	    };
	  },

	  components: {},
	  computed: {},
	  events: {},
	  mounted: function mounted() {
	    var self = this;
	    $.ajax({
	      url: 'http://maka.im/app/cities',
	      type: 'GET',
	      dataType: 'json',
	      data: {},
	      success: function success(data) {
	        self.provinceCity = data.data.dataList;
	      },
	      error: function error(_error) {
	        console.log(_error);
	      }
	    });
	    $.ajax({
	      url: _config2.default.API_V4_BUSINESS_INDUSTRY_LIST,
	      type: 'GET',
	      dataType: 'json',
	      data: {},
	      success: function success(data) {
	        self.industries = data.data;
	      },
	      error: function error(_error2) {
	        console.log(_error2);
	      }
	    });
	    $('.industry').focus(function () {
	      self.sendErrorTips = '';
	    });
	    $('.province').focus(function () {
	      self.sendErrorTips = '';
	    });
	    $('.city').focus(function () {
	      self.sendErrorTips = '';
	    });
	    $('#company').focus(function () {
	      self.sendErrorTips = '';
	      self.companyErrorTips = '';
	    });
	    $('#name').focus(function () {
	      self.sendErrorTips = '';
	    });
	    $('#position').focus(function () {
	      self.sendErrorTips = '';
	    });
	    $('#email').focus(function () {
	      self.sendErrorTips = '';
	      self.emailErrorTips = '';
	    });
	    $('#phone').focus(function () {
	      self.sendErrorTips = '';
	      self.phoneErrorTips = '';
	    });

	    self.$watch('verifyCode', function (newVal, oldVal) {
	      self.sendErrorTips = '';
	      self.verifyCodeErrorTips = '';
	    });
	  },

	  methods: {
	    checkAll: function checkAll() {
	      var self = this;
	      if (self.industry == '') {
	        self.sendErrorTips = '请选择所属行业';
	        return false;
	      }
	      if (self.province == '') {
	        self.sendErrorTips = '请选择省份';
	        return false;
	      }
	      if (self.city == '') {
	        self.sendErrorTips = '请选择城市';
	        return false;
	      }
	      if (self.company == '') {
	        self.companyErrorTips = '请填写公司名';
	        return false;
	      }
	      if (self.name == '') {
	        self.sendErrorTips = '请填写联系人姓名';
	        return false;
	      }
	      if (self.position == '') {
	        self.sendErrorTips = '请填写联系人职位';
	        return false;
	      }
	      if (!_regExp2.default.email.test(self.email)) {
	        self.emailErrorTips = '请输入正确的邮箱地址';
	        return false;
	      }
	      if (!_regExp2.default.mobile.test(self.phone)) {
	        self.phoneErrorTips = '请填写正确的手机号';
	        return false;
	      }
	      if (self.verifyCode == '') {
	        self.verifyCodeErrorTips = '请填写验证码';
	        return false;
	      }
	      return true;
	    },
	    checkPhone: function checkPhone() {
	      var self = this;
	      if (!_regExp2.default.mobile.test(self.phone)) {
	        self.phoneErrorTips = '请填写正确的手机号';
	        return false;
	      }
	      return true;
	    },
	    send: function send() {
	      var self = this;
	      self.checkAll();
	      if (self.checkAll()) {
	        $.ajax({
	          url: _config2.default.API_V4_BUSINESS_PRIMARY_VERIFY,
	          type: 'POST',
	          dataType: 'json',
	          data: { industry: self.industry, province: self.province, city: self.city, business_name: self.company, first_name: self.name, position: self.position, email: self.email, phone: self.phone, code: self.verifyCode },
	          success: function success(result) {
	            if (result.code == 200) {
	              self.identifySuccess = true;
	            } else {
	              self.sendErrorTips = result.message;
	            }
	          },
	          error: function error(_error3) {
	            console.log(_error3);
	            self.sendErrorTips = '网络错误';
	          }
	        });
	      }
	    },
	    resetTimer: function resetTimer(msg) {
	      clearInterval(this.timer);
	      this.getcode = msg;
	      this.counting = false;
	    },
	    getVerifyCode: function getVerifyCode() {
	      var self = this;
	      //正在读秒的话不能点击
	      if (self.counting || self.phone == undefined) {
	        return;
	      }
	      console.log(self.phone);
	      if (self.checkPhone()) {
	        this.getcode = '获取中...';
	        $.ajax({
	          url: _config2.default.API_ADMIN_VERIFY,
	          type: 'POST',
	          dataType: 'json',
	          data: {
	            mobile: self.phone
	          },
	          success: function success(result) {
	            var msg = result.message;
	            //接口返回一些错误码，不进行读数操作，直接返回
	            if (result.code == 200) {
	              //设置读秒内容，置灰
	              var second = 60;
	              self.counting = true;
	              self.getcode = second + "s后重试";

	              self.timer = setInterval(function () {
	                if (second == 0) {
	                  self.resetTimer('重新获取');
	                } else {
	                  second--;
	                  self.getcode = second + "s后重试";
	                }
	              }, 1000);
	            } else {
	              self.accountRegErrorTips = msg;
	              self.resetTimer('重新获取');
	            }
	          },
	          error: function error(_error4) {
	            self.accountRegErrorTips = '网络错误';
	            self.resetTimer('重新获取');
	          }
	        });
	      }
	    },
	    toIdentityMore: function toIdentityMore() {
	      this.show.seniorIdentity = true;
	    },
	    returnTo: function returnTo() {
	      var self = this;
	      location.href = '/user/info';
	    },
	    successAll: function successAll() {
	      var self = this;
	      this.$emit('closeAll', true);
	    }
	  }
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ },
/* 126 */
/***/ function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    directives: [{
	      name: "show",
	      rawName: "v-show",
	      value: (_vm.show.primaryIdentify),
	      expression: "show.primaryIdentify"
	    }],
	    staticClass: "primaryIdentifyModel"
	  }, [(!_vm.identifySuccess) ? _c('div', {
	    staticClass: "model"
	  }, [_c('div', {
	    staticClass: "top"
	  }, [_c('span', [_vm._v("企业初级认证")]), (!_vm.show.noClose) ? _c('div', {
	    staticClass: "closeBtn",
	    on: {
	      "click": _vm.successAll
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })]) : _vm._e()]), _c('div', {
	    staticClass: "topDesc"
	  }, [_vm._v("完成初级认证，获得福利礼包，为您推荐更适合的模版。")]), _vm._m(0), _c('div', {
	    staticClass: "middleDesc"
	  }, [_vm._v("你还需填写以下信息，完成企业用户的认证，以便我们提供更好的服务。")]), _c('div', {
	    staticClass: "mainTitle"
	  }, [_vm._v("所属行业")]), _c('div', {
	    staticClass: "mainInput"
	  }, [_c('select', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.industry),
	      expression: "industry"
	    }],
	    staticClass: "industry",
	    on: {
	      "change": function($event) {
	        _vm.industry = Array.prototype.filter.call($event.target.options, function(o) {
	          return o.selected
	        }).map(function(o) {
	          var val = "_value" in o ? o._value : o.value;
	          return val
	        })[0]
	      }
	    }
	  }, [_c('option', {
	    attrs: {
	      "value": "none"
	    }
	  }, [_vm._v("点击选择所属行业")]), _vm._l((_vm.industries), function(val) {
	    return _c('option', {
	      domProps: {
	        "value": val
	      }
	    }, [_vm._v(_vm._s(val))])
	  })], 2)]), _c('div', {
	    staticClass: "mainTitle"
	  }, [_vm._v("公司／机构名称")]), _c('div', {
	    staticClass: "mainInput"
	  }, [_c('select', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.province),
	      expression: "province"
	    }],
	    staticClass: "province",
	    on: {
	      "change": function($event) {
	        _vm.province = Array.prototype.filter.call($event.target.options, function(o) {
	          return o.selected
	        }).map(function(o) {
	          var val = "_value" in o ? o._value : o.value;
	          return val
	        })[0]
	      }
	    }
	  }, [_c('option', {
	    attrs: {
	      "value": "none"
	    }
	  }, [_vm._v("省")]), _vm._l((_vm.provinceCity), function(value, key) {
	    return _c('option', {
	      domProps: {
	        "value": key
	      }
	    }, [_vm._v(_vm._s(key))])
	  })], 2), _c('select', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.city),
	      expression: "city"
	    }],
	    staticClass: "city",
	    on: {
	      "change": function($event) {
	        _vm.city = Array.prototype.filter.call($event.target.options, function(o) {
	          return o.selected
	        }).map(function(o) {
	          var val = "_value" in o ? o._value : o.value;
	          return val
	        })[0]
	      }
	    }
	  }, [_c('option', {
	    attrs: {
	      "value": "none"
	    }
	  }, [_vm._v("市")]), _vm._l((_vm.provinceCity[_vm.province]), function(city) {
	    return _c('option', {
	      domProps: {
	        "value": city
	      }
	    }, [_vm._v(_vm._s(city))])
	  })], 2)]), _c('div', {
	    staticClass: "inputArea companyInput"
	  }, [_c('i', {
	    staticClass: "passwordIcon icon-code"
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.company),
	      expression: "company"
	    }],
	    attrs: {
	      "id": "company",
	      "type": "text",
	      "placeholder": "(范例) 深圳格莱珉文化传播有限公司"
	    },
	    domProps: {
	      "value": _vm._s(_vm.company)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.company = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.companyErrorTips))])]), _c('div', {
	    staticClass: "mainTitle"
	  }, [_vm._v("联系方式")]), _c('div', {
	    staticClass: "inputArea nameInput"
	  }, [_c('i', {
	    staticClass: "personIcon icon-contacts"
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.name),
	      expression: "name"
	    }],
	    attrs: {
	      "id": "name",
	      "type": "text",
	      "placeholder": "联系人姓名"
	    },
	    domProps: {
	      "value": _vm._s(_vm.name)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.name = $event.target.value
	      }
	    }
	  }), _c('i', {
	    staticClass: "positionIcon icon-job"
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.position),
	      expression: "position"
	    }],
	    attrs: {
	      "id": "position",
	      "type": "text",
	      "placeholder": "联系人职位"
	    },
	    domProps: {
	      "value": _vm._s(_vm.position)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.position = $event.target.value
	      }
	    }
	  })]), _c('div', {
	    staticClass: "inputArea emailInput"
	  }, [_c('i', {
	    staticClass: "emailIcon icon-email"
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.email),
	      expression: "email"
	    }],
	    attrs: {
	      "id": "email",
	      "type": "text",
	      "placeholder": "请输入电子邮箱"
	    },
	    domProps: {
	      "value": _vm._s(_vm.email)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.email = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.emailErrorTips))])]), _c('div', {
	    staticClass: "tel inputArea"
	  }, [_c('i', {
	    staticClass: "phoneIcon icon-phonecall"
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.phone),
	      expression: "phone"
	    }],
	    attrs: {
	      "id": "phone",
	      "type": "text",
	      "placeholder": "手机号码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.phone)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.phone = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.phoneErrorTips))])]), _c('div', {
	    staticClass: "verifyCode inputArea"
	  }, [_c('i', {
	    staticClass: "codeIcon icon-code"
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.verifyCode),
	      expression: "verifyCode"
	    }],
	    attrs: {
	      "id": "verifyCode",
	      "type": "text",
	      "placeholder": "验证码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.verifyCode)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.verifyCode = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "verifyCodeBtn",
	    class: _vm.counting || _vm.phone == undefined ? "disable" : "",
	    on: {
	      "click": _vm.getVerifyCode
	    }
	  }, [_vm._v(_vm._s(_vm.getcode))]), _c('div', {
	    staticClass: "errorTips",
	    staticStyle: {
	      "right": "165px"
	    }
	  }, [_vm._v(_vm._s(_vm.verifyCodeErrorTips))])]), _c('div', {
	    staticClass: "sendErrorTips"
	  }, [_vm._v(_vm._s(_vm.sendErrorTips))]), _c('div', {
	    staticClass: "sendBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    on: {
	      "click": _vm.send
	    }
	  }, [_vm._v("提交")])])]) : _c('div', {
	    staticClass: "model successModel"
	  }, [_c('div', {
	    staticClass: "top"
	  }, [_c('span', [_vm._v("认证成功")]), (!_vm.show.noClose) ? _c('div', {
	    staticClass: "closeBtn",
	    on: {
	      "click": _vm.successAll
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })]) : _vm._e()]), _c('div', {
	    staticClass: "successDesc"
	  }, [_vm._v("模版将发放到您的账号上，请您在“已购模板”内查收！")]), _c('div', {
	    staticClass: "sendBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    on: {
	      "click": _vm.toIdentityMore
	    }
	  }, [_vm._v("继续高级认证")])]), _c('div', {
	    staticClass: "cancelBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    on: {
	      "click": _vm.returnTo
	    }
	  }, [_vm._v("返回 ")])])])])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "topInfo"
	  }, [_c('div', {
	    staticClass: "topInfo-left"
	  }, [_c('img', {
	    attrs: {
	      "src": __webpack_require__(127)
	    }
	  }), _c('span', [_vm._v("赠送指定热销模版2个")])]), _c('div', {
	    staticClass: "topInfo-right"
	  }, [_c('img', {
	    attrs: {
	      "src": __webpack_require__(128)
	    }
	  }), _c('span', [_vm._v("根据喜好推荐模版")])])])
	}]}

/***/ },
/* 127 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "275ef3222b76fa7834da0910ace15871.png";

/***/ },
/* 128 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "616dbfb2840871c990dac3f3199384cb.png";

/***/ },
/* 129 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(130)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(132),
	  /* template */
	  __webpack_require__(222),
	  /* scopeId */
	  "data-v-49fbd2a0",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 130 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(131);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("0bc81df4", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-49fbd2a0&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./seniorIdentityModel.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-49fbd2a0&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./seniorIdentityModel.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 131 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".seniorIdentityModel[data-v-49fbd2a0]{position:absolute;background:hsla(0,0%,100%,.6);top:0;bottom:0;left:0;right:0;z-index:999}.model[data-v-49fbd2a0]{width:402px;position:absolute;background:#fff;box-shadow:0 2px 10px 0 rgba(0,0,0,.3);border-radius:6px;left:0;right:0;top:80px;margin:auto}.model .top[data-v-49fbd2a0]{width:100%;height:71px;position:relative;border-radius:6px 6px 0 0;text-align:center}.model .top span[data-v-49fbd2a0]{font-size:24px;color:#4a4a4a;line-height:71px}.model .top .closeBtn[data-v-49fbd2a0]{position:absolute;width:24px;height:24px;background:#fff;border:1px solid #a2a2a2;border-radius:100%;color:#a2a2a2;line-height:24px;font-size:10px;right:9px;top:9px;margin:auto;cursor:pointer;text-align:center;box-sizing:border-box}.model .top .closeBtn[data-v-49fbd2a0]:hover{color:#fff;border:1px solid #ff7272;background:#ff7272}.model .top .closeBtn i[data-v-49fbd2a0]{padding-left:1.5px;line-height:inherit}.model .topDesc[data-v-49fbd2a0]{width:100%;height:20px;font-size:14px;color:#4a4a4a;text-align:center}.model .topDesc i[data-v-49fbd2a0]{color:#ffc900}.model .topDesc span[data-v-49fbd2a0]{padding-left:7px}.model .topInfo[data-v-49fbd2a0]{width:100%;height:71px;padding:0 26px;box-sizing:border-box;margin-top:10px}.model .topInfo-left[data-v-49fbd2a0],.model .topInfo-middle[data-v-49fbd2a0],.model .topInfo-right[data-v-49fbd2a0]{width:110px;height:71px;float:left;background:#fdfdfd;border-radius:3px}.model .topInfo-left img[data-v-49fbd2a0],.model .topInfo-middle img[data-v-49fbd2a0],.model .topInfo-right img[data-v-49fbd2a0]{float:left;margin-top:14px;margin-left:10px}.model .topInfo-left span[data-v-49fbd2a0],.model .topInfo-middle span[data-v-49fbd2a0],.model .topInfo-right span[data-v-49fbd2a0]{float:left;font-size:12px;color:#4a4a4a;line-height:18px;margin-top:18px;margin-left:10px}.model .topInfo-left span[data-v-49fbd2a0]{margin-left:7px;width:69px}.model .topInfo-middle span[data-v-49fbd2a0]{width:53px}.model .topInfo-right span[data-v-49fbd2a0]{width:48px}.model .topInfo-middle[data-v-49fbd2a0]{margin-left:10px}.model .topInfo-right[data-v-49fbd2a0]{float:right}.model .middleDesc[data-v-49fbd2a0]{font-size:12px;color:#4a4a4a;width:350px;margin-left:26px;border-top:1px dashed #18ccc0;padding-top:11px;margin-top:15px}.model .mainTitle[data-v-49fbd2a0]{width:200px;height:21px;font-size:16px;color:#4a4a4a;padding-left:5px;border-left:3px solid #18ccc0;margin-left:26px;margin-top:20px}.model .sceneArea[data-v-49fbd2a0]{width:100%;height:62px;padding:0 26px;box-sizing:border-box;margin-top:7px}.model .sceneArea .scene[data-v-49fbd2a0]{background:#fdfdfd;border:1px solid #efefef;width:76px;height:24px;border-radius:2px;box-sizing:border-box;font-size:12px;color:#4a4a4a;line-height:24px;text-align:center;float:left;margin-right:12px;margin-bottom:7px;cursor:pointer}.model .sceneArea .active[data-v-49fbd2a0]{color:#fff;background:#18ccc0;border:0 solid #fff}.model .sceneArea .none[data-v-49fbd2a0]{background:#efefef;border:1px solid #efefef;color:#a2a2a2}.model .mainInput[data-v-49fbd2a0]{margin-top:7px;width:100%;height:22px}.model .mainInput select[data-v-49fbd2a0]{background:#fdfdfd;border:1px solid #efefef;border-radius:2px;font-size:12px;color:#4a4a4a;line-height:12px}.model .mainInput select[data-v-49fbd2a0]:focus,.model .mainInput select[data-v-49fbd2a0]:hover{border:1px solid #cbcbcb}.model .mainInput select.size[data-v-49fbd2a0]{position:absolute;width:350px;left:0;right:0;margin:auto}.model .mainInput select.city[data-v-49fbd2a0],.model .mainInput select.province[data-v-49fbd2a0]{width:96px;float:left}.model .mainInput select.province[data-v-49fbd2a0]{margin-left:26px}.model .mainInput select.city[data-v-49fbd2a0]{margin-left:10px}.model .imgInput[data-v-49fbd2a0]{position:relative;width:350px;height:60px;margin-top:7px;padding:0 26px}.model .imgInput .imgArea[data-v-49fbd2a0]{position:relative;width:60px;height:60px;margin-right:12px;float:left}.model .imgInput .imgArea .delBtn[data-v-49fbd2a0]{position:absolute;width:16px;height:16px;background:#fff;border:1px solid #fff;border-radius:100%;top:-8px;right:-8px;text-align:center;line-height:16px;font-size:16px}.model .imgInput .imgArea .delBtn i[data-v-49fbd2a0]{color:#ff6565}.model .imgInput .imgArea img[data-v-49fbd2a0]{width:60px;height:60px}.model .imgInput .addImgBtn[data-v-49fbd2a0]{width:60px;height:60px;background:#fdfdfd;border:1px dashed #c1c1c1;border-radius:2px;box-sizing:border-box;text-align:center;line-height:60px;cursor:pointer;float:left}.model .imgInput .addImgBtn i[data-v-49fbd2a0]{font-size:25px;color:#18ccc0;line-height:inherit}.model .imgInput .addImgBtn input[data-v-49fbd2a0],.model .imgInput .disable[data-v-49fbd2a0]{display:none}.model .companyInput[data-v-49fbd2a0],.model .otherFocusInput[data-v-49fbd2a0],.model .otherSceneInput[data-v-49fbd2a0]{position:relative;width:100%;height:34px}.model .companyInput[data-v-49fbd2a0]{margin-top:10px}.model .inputArea i[data-v-49fbd2a0]{position:absolute;top:0;bottom:0;margin:auto;line-height:34px;color:#4a4a4a;margin-left:34px;z-index:1}.model .inputArea i.phoneIcon[data-v-49fbd2a0]{font-size:18px}.model .inputArea i.codeIcon[data-v-49fbd2a0]{font-size:15px}.model .inputArea i.passwordIcon[data-v-49fbd2a0]{font-size:12px;margin-left:36px}.model .inputArea input[data-v-49fbd2a0]{position:absolute;background:#fdfdfd;border-radius:3px;border:1px solid #efefef;width:342px;height:30px;outline:none;padding-left:6px;font-size:12px;color:#000;line-height:34px;left:0;right:0;top:0;margin:auto}.model .inputArea input[data-v-49fbd2a0]:hover{border:1px solid #cbcbcb}.model .inputArea input[data-v-49fbd2a0]:focus{border:1px solid #18ccc0;box-shadow:0 0 3px 0 rgba(105,255,244,.8)}.model .inputArea input[data-v-49fbd2a0]::-webkit-input-placeholder{color:#4a4a4a}.model .inputArea input.error[data-v-49fbd2a0]{border:1px solid #f67171}.model .inputArea input.error[data-v-49fbd2a0]::-webkit-input-placeholder{color:#f67171}.model .inputArea .errorTips[data-v-49fbd2a0]{position:absolute;font-size:12px;color:#f67171;line-height:33px;right:34px}.model .nameInput[data-v-49fbd2a0]{position:relative;width:100%;height:34px;margin-top:10px}.model .nameInput input#name[data-v-49fbd2a0]{width:100px;left:26px;right:100%}.model .nameInput input#position[data-v-49fbd2a0]{width:100px;left:170px;right:100%}.model .emailInput[data-v-49fbd2a0]{position:relative;width:350px;height:51px;margin-top:10px;margin-left:26px;padding-bottom:16px;border-bottom:1px dashed #979797;box-sizing:border-box}.model .tel[data-v-49fbd2a0]{position:relative;width:100%;height:34px;margin-top:14px}.model .verifyCode[data-v-49fbd2a0]{position:relative;width:100%;height:34px;margin-top:12px}.model .verifyCode input[data-v-49fbd2a0]{left:25px;right:100%;width:190px}.model .verifyCode .verifyCodeBtn[data-v-49fbd2a0]{position:absolute;width:120px;height:34px;background:#18ccc0;border-radius:2px;font-size:12px;color:#fff;line-height:34px;text-align:center;cursor:pointer;right:26px;top:0;bottom:0;margin:auto}.model .verifyCode .disable[data-v-49fbd2a0]{background:#ccc}.model .sendErrorTips[data-v-49fbd2a0]{width:100%;height:22px;font-size:12px;color:#f67171;float:right;line-height:22px;padding-left:26px;box-sizing:border-box}.model .sendBtn[data-v-49fbd2a0]{position:relative;width:100%;height:44px;margin-top:22px;margin-bottom:26px}.model .sendBtn .btn[data-v-49fbd2a0]{position:absolute;width:350px;height:42px;background:#18ccc0;border:1px solid #18ccc0;border-radius:2px;left:0;right:0;top:0;bottom:0;margin:auto;font-size:14px;color:#fff;line-height:42px;text-align:center;cursor:pointer}.model .sendBtn .lock[data-v-49fbd2a0]{background:#efefef;border:1px solid #efefef}.model .cancelBtn[data-v-49fbd2a0]{position:relative;width:100%;height:44px;margin-top:22px;margin-bottom:26px}.model .cancelBtn .btn[data-v-49fbd2a0]{position:absolute;width:350px;height:42px;background:#fff;border:1px solid #18ccc0;border-radius:2px;left:0;right:0;top:0;bottom:0;margin:auto;font-size:14px;color:#18ccc0;line-height:42px;text-align:center;cursor:pointer}.successModel[data-v-49fbd2a0]{margin-top:200px}.successModel .successDesc[data-v-49fbd2a0]{font-size:14px;color:#4a4a4a;padding:0 26px;box-sizing:border-box}", ""]);

	// exports


/***/ },
/* 132 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _regExp = __webpack_require__(107);

	var _regExp2 = _interopRequireDefault(_regExp);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	// import ossService from '../utils/OssService.js'
	// import crypto from 'crypto'
	// import html5ImgCompress from '../utils/html5-image-compress/src/html5ImgCompress.js'

	exports.default = {
	  props: ['show'],
	  data: function data() {
	    return {
	      size: '',
	      sizeList: {},
	      sceneList: {},
	      focusList: {},
	      upLoading: false,
	      otherScene: false,
	      sceneNum: 0,
	      otherFocus: false,
	      focusNum: 0,
	      imgBase64: [],
	      cropBlob: [],
	      sendScene: [],
	      otherSceneText: '',
	      sendFocus: [],
	      otherFocusText: '',
	      sendFiles: [],
	      businessLicense: [],
	      sendErrorTips: '',
	      lock: false,
	      sendText: '提交',
	      identifySuccess: false,
	      otherFocusErrorTips: ''
	    };
	  },

	  components: {},
	  computed: {},
	  events: {},
	  mounted: function mounted() {
	    var self = this;
	    $.ajax({
	      url: _config2.default.API_V4_BUSINESS_SENIOR_OPTION_LIST,
	      type: 'GET',
	      dataType: 'json',
	      data: {},
	      success: function success(data) {
	        self.sizeList = data.data.size_list;
	        var scene_list = data.data.scene_list;
	        var focus_list = data.data.focus_list;
	        var sceneList = {};
	        var focusList = {};
	        for (var key in scene_list) {
	          sceneList[key] = {};
	          sceneList[key]['name'] = scene_list[key];
	          sceneList[key]['selected'] = false;
	        }
	        for (var _key in focus_list) {
	          focusList[_key] = {};
	          focusList[_key]['name'] = focus_list[_key];
	          focusList[_key]['selected'] = false;
	        }
	        self.sceneList = sceneList;
	        self.focusList = focusList;
	      },
	      error: function error(_error) {
	        console.log(_error);
	      }
	    });
	    $('.size').focus(function () {
	      self.sendErrorTips = '';
	    });
	  },

	  methods: {
	    selectScene: function selectScene(key) {
	      var self = this;
	      self.sendErrorTips = '';
	      var list = self.sceneList;
	      var num = self.sceneNum;
	      if (!list[key].selected && num >= 3) return;
	      list[key].selected = !list[key].selected;
	      if (list[key].selected) {
	        num = num + 1;
	        self.sendScene.push(list[key].name);
	      } else {
	        num = num - 1;
	        for (var index in self.sendScene) {
	          if (self.sendScene[index] == list[key].name) {
	            self.sendScene.splice(index, 1);
	          }
	        }
	      }
	      self.sceneNum = num;
	      self.sceneList = list;
	    },
	    selectOtherScene: function selectOtherScene() {
	      var self = this;
	      self.sendErrorTips = '';
	      var other = self.otherScene;
	      var num = self.sceneNum;
	      if (!other && num >= 3) return;
	      other = !other;
	      num = other ? num + 1 : num - 1;
	      self.sceneNum = num;
	      self.otherScene = other;
	      if (!other) {
	        self.otherSceneText = '';
	      }
	    },
	    selectFocus: function selectFocus(key) {
	      var self = this;
	      self.sendErrorTips = '';
	      var list = self.focusList;
	      var num = self.focusNum;
	      if (!list[key].selected && num >= 3) return;
	      list[key].selected = !list[key].selected;
	      if (list[key].selected) {
	        num = num + 1;
	        self.sendFocus.push(list[key].name);
	      } else {
	        num = num - 1;
	        for (var index in self.sendFocus) {
	          if (self.sendFocus[index] == list[key].name) {
	            self.sendFocus.splice(index, 1);
	          }
	        }
	      }
	      self.focusNum = num;
	      self.focusList = list;
	    },
	    selectOtherFocus: function selectOtherFocus() {
	      var self = this;
	      self.sendErrorTips = '';
	      var other = self.otherFocus;
	      var num = self.focusNum;
	      if (!other && num >= 3) return;
	      other = !other;
	      num = other ? num + 1 : num - 1;
	      self.focusNum = num;
	      self.otherFocus = other;
	      if (!other) {
	        self.otherFocusText = '';
	      }
	    },
	    upLoad: function upLoad() {
	      var self = this;
	      if (self.upLoading) return;
	      self.upLoading = true;
	      self.sendErrorTips = '';
	      $('#uploadImg').click();
	      self.upLoading = false;
	    },
	    fileOnselect: function fileOnselect() {
	      var self = this,
	          files = void 0,
	          file = void 0,
	          url = void 0;
	      files = $('#uploadImg').prop('files');
	      // 校验是否为图片文件，校验不通过需要给出错误提示，并将input的value置空
	      function validateImg(file) {
	        if (['image/jpeg', 'image/png', 'image/gif'].indexOf(file.type) === -1) {
	          /*这里需要给出文件错误提示*/
	          console.log('非图片文件');
	          return false;
	        }
	        return true;
	      }

	      __webpack_require__.e/* nsure */(1, function (require) {
	        var html5ImgCompress = __webpack_require__(133);

	        if (files.length > 0) {
	          // 如果要上传大量文件, 需要自己控制并发
	          for (var i = 0; i < files.length; i++) {
	            if (validateImg(files[i]) /*  && validateFileSize(files[i], 2)*/) {
	                if (files[i].type === 'image/gif') {
	                  self.upLoading = false;
	                  return;
	                } else {
	                  new html5ImgCompress(files[i], {
	                    maxHeight: 1200,
	                    maxWidth: 1200,
	                    quality: 0.9,
	                    before: function before(file) {
	                      // console.debug('压缩前...');
	                      // 这里一般是对file进行filter，例如用file.type.indexOf('image') > -1来检验是否是图片
	                      // 如果为非图片，则return false放弃压缩（不执行后续done、fail、complate），并相应提示
	                    },
	                    done: function done(file, base64, fileType) {
	                      // console.debug('压缩成功...' + fileType);
	                      // ajax和服务器通信上传base64图片等操作
	                      var blob = _utils2.default.convertBase64ToBlob(base64);
	                      self.sendFiles.push(file);
	                      self.cropBlob.push(blob);
	                      self.imgBase64.push(base64);
	                      /*url = URL.createObjectURL(blob)
	                      $image.cropper('replace', url)*/
	                    },
	                    fail: function fail(file) {
	                      console.error('压缩失败(press fail)...');
	                    },
	                    complate: function complate(file) {
	                      console.log('压缩完成(press done)...');
	                      self.upLoading = false;
	                    },
	                    notSupport: function notSupport(file) {
	                      console.log('浏览器不支持！(your browser donot surport for press!)');
	                      // 不支持操作，例如PC在这里可以采用swfupload上传
	                    }
	                  });
	                }
	              }
	          }
	        }
	      });
	    },
	    delImg: function delImg(key) {
	      var self = this;
	      self.sendFiles.splice(key, 1);
	      self.cropBlob.splice(key, 1);
	      self.imgBase64.splice(key, 1);
	    },
	    checkAll: function checkAll() {
	      var self = this;
	      if (self.size == '') {
	        self.sendErrorTips = '请选择公司规模';
	        return false;
	      }
	      if (!self.sendFiles.length > 0) {
	        self.sendErrorTips = '请上传营业执照或其他证明文件';
	        return false;
	      }
	      if (!(self.sendScene.length > 0 || self.otherSceneText != '')) {
	        self.sendErrorTips = '请选择或填写您使用的场景';
	        return false;
	      }
	      if (!(self.sendFocus.length > 0 || self.otherFocusText != '')) {
	        self.sendErrorTips = '请选择或填写您关注的功能';
	        return false;
	      }
	      return true;
	    },
	    send: function send() {
	      var self = this;
	      if (self.lock) return;
	      if (self.checkAll()) {
	        self.sendText = '提交中...';
	        self.lock = true;
	        if (self.businessLicense.length != 0) {
	          self.submit();
	        } else {
	          var _loop = function _loop(key) {
	            var files = self.sendFiles[key];
	            var fileName = void 0;
	            var tail = void 0;
	            var fileType = files.type;
	            __webpack_require__.e/* nsure */(3, function (require) {
	              var crypto = __webpack_require__(135);
	              var md5 = crypto.createHash('md5');
	              var md5string = files.type + files.name + files.lastModified + files.size;
	              md5.update(md5string);
	              // elem.attr('disabled', 'disabled')
	              var blob = self.cropBlob[key];
	              if (fileType === 'image/jpeg') {
	                tail = '.jpg';
	              } else if (fileType === 'image/png') {
	                tail = '.png';
	              }
	              fileName = 'business/' + md5.digest('hex') + tail;
	              // require.ensure(['../utils/OssService.js'], require => {
	              var ossService = __webpack_require__(194).default;
	              console.log(ossService);
	              ossService.putToOss(fileName, blob, {
	                progress: self.uploadProgress,
	                complete: self.uploadComplete
	              });
	              // })
	            });
	          };

	          for (var key in self.sendFiles) {
	            _loop(key);
	          }
	        }
	      }
	    },
	    uploadProgress: function uploadProgress(res, key) {
	      console.log('上传中');
	    },
	    uploadComplete: function uploadComplete(res, key) {
	      // 上传完成后需要记录图片文件的路径，拼接裁图服务的api
	      // http://test.img.maka.im/user/1091436/thumb/4cfd9ff3c16e6941a7f25d2f5666915d.jpg@500w%7C100-20-300-100a
	      // http://img1.maka.im/user/1091436/thumb/4cfd9ff3c16e6941a7f25d2f5666915d.jpg@500w%7C100-20-300-100a
	      // self.thumb = Config.Oss.cropcdn+ key + cropArea
	      // 这里就需要知道更新接口去更新USER_DETAIL服务器上的USER_DETAIL
	      var self = this;
	      var thumb = _config2.default.Oss.cropcdn + key;
	      self.businessLicense.push(thumb);
	      //都上传成功才进行提交
	      if (self.businessLicense.length == self.sendFiles.length) {
	        self.submit();
	      }
	      // 记录后设置img的src为该路径, 并关闭croper框
	    },
	    submit: function submit() {
	      var self = this;
	      $.ajax({
	        url: _config2.default.API_V4_BUSINESS_SENIOR_VERIFY,
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          firm_size: self.size,
	          business_license: self.businessLicense,
	          scene: self.sendScene,
	          other_scene: self.otherSceneText,
	          focus: self.sendFocus,
	          other_focus: self.otherFocusText
	        },
	        success: function success(result) {
	          if (result.code == 200) {
	            self.identifySuccess = true;
	          } else {
	            self.lock = false;
	            self.sendText = '提交';
	            self.sendErrorTips = result.message;
	          }
	        },
	        error: function error(_error2) {
	          console.log(_error2);
	          self.lock = false;
	          self.sendText = '提交';
	          self.sendErrorTips = '网络错误';
	        }
	      });
	    },
	    successAll: function successAll() {
	      var self = this;
	      location.href = location.pathname;
	      this.$emit('closeAll', true);
	    }
	  }
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ },
/* 133 */,
/* 134 */,
/* 135 */,
/* 136 */,
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */,
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */,
/* 190 */,
/* 191 */,
/* 192 */,
/* 193 */,
/* 194 */,
/* 195 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = { "default": __webpack_require__(196), __esModule: true };

/***/ },
/* 196 */
/***/ function(module, exports, __webpack_require__) {

	var core  = __webpack_require__(7)
	  , $JSON = core.JSON || (core.JSON = {stringify: JSON.stringify});
	module.exports = function stringify(it){ // eslint-disable-line no-unused-vars
	  return $JSON.stringify.apply($JSON, arguments);
	};

/***/ },
/* 197 */,
/* 198 */,
/* 199 */,
/* 200 */,
/* 201 */,
/* 202 */,
/* 203 */,
/* 204 */,
/* 205 */,
/* 206 */,
/* 207 */,
/* 208 */,
/* 209 */,
/* 210 */,
/* 211 */,
/* 212 */,
/* 213 */,
/* 214 */,
/* 215 */,
/* 216 */,
/* 217 */,
/* 218 */,
/* 219 */,
/* 220 */,
/* 221 */,
/* 222 */
/***/ function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    directives: [{
	      name: "show",
	      rawName: "v-show",
	      value: (_vm.show.seniorIdentity),
	      expression: "show.seniorIdentity"
	    }],
	    staticClass: "seniorIdentityModel"
	  }, [(!_vm.identifySuccess) ? _c('div', {
	    staticClass: "model"
	  }, [_c('div', {
	    staticClass: "top"
	  }, [_c('span', [_vm._v("企业高级认证")]), (!_vm.show.noClose) ? _c('div', {
	    staticClass: "closeBtn",
	    on: {
	      "click": _vm.successAll
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })]) : _vm._e()]), _vm._m(0), _vm._m(1), _c('div', {
	    staticClass: "middleDesc"
	  }, [_vm._v("你还需填写以下信息，完成企业用户的认证，以便我们提供更好的服务。")]), _c('div', {
	    staticClass: "mainTitle"
	  }, [_vm._v("公司规模")]), _c('div', {
	    staticClass: "mainInput"
	  }, [_c('select', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.size),
	      expression: "size"
	    }],
	    staticClass: "size",
	    on: {
	      "change": function($event) {
	        _vm.size = Array.prototype.filter.call($event.target.options, function(o) {
	          return o.selected
	        }).map(function(o) {
	          var val = "_value" in o ? o._value : o.value;
	          return val
	        })[0]
	      }
	    }
	  }, [_c('option', {
	    attrs: {
	      "value": ""
	    }
	  }, [_vm._v("点击选择公司规模")]), _vm._l((_vm.sizeList), function(value, key) {
	    return _c('option', {
	      domProps: {
	        "value": key
	      }
	    }, [_vm._v(_vm._s(value))])
	  })], 2)]), _vm._m(2), _c('div', {
	    staticClass: "imgInput"
	  }, [_vm._l((_vm.imgBase64), function(val, key) {
	    return _c('div', {
	      staticClass: "imgArea"
	    }, [_c('img', {
	      attrs: {
	        "src": val
	      }
	    }), _c('div', {
	      staticClass: "delBtn",
	      on: {
	        "click": function($event) {
	          _vm.delImg(key)
	        }
	      }
	    }, [_c('i', {
	      staticClass: "icon-ic_error"
	    })])])
	  }), (_vm.imgBase64.length < 3) ? _c('div', {
	    staticClass: "addImgBtn",
	    on: {
	      "click": _vm.upLoad
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_add"
	  }), _c('input', {
	    ref: "input",
	    attrs: {
	      "id": "uploadImg",
	      "type": "file",
	      "accept": "image/jpeg, image/png",
	      "capture": "camera"
	    },
	    on: {
	      "change": _vm.fileOnselect
	    }
	  })]) : _vm._e()], 2), _c('div', {
	    staticClass: "mainTitle"
	  }, [_vm._v("使用场景")]), _c('div', {
	    staticClass: "sceneArea"
	  }, [_vm._l((_vm.sceneList), function(value, key) {
	    return _c('div', {
	      staticClass: "scene",
	      class: value.selected == true ? "active" : _vm.sceneNum == 3 ? "none" : "",
	      style: (key % 4 == 0 ? "margin-right:0px;" : ""),
	      on: {
	        "click": function($event) {
	          _vm.selectScene(key)
	        }
	      }
	    }, [_vm._v(_vm._s(value.name) + " ")])
	  }), _c('div', {
	    staticClass: "scene",
	    class: _vm.otherScene ? "active" : _vm.sceneNum == 3 ? "none" : "",
	    staticStyle: {
	      "margin-right": "0px"
	    },
	    on: {
	      "click": _vm.selectOtherScene
	    }
	  }, [_vm._v("其它")])], 2), (_vm.otherScene) ? _c('div', {
	    staticClass: "inputArea otherSceneInput"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.otherSceneText),
	      expression: "otherSceneText"
	    }],
	    attrs: {
	      "id": "otherSceneText",
	      "type": "text",
	      "placeholder": "请填写其他您使用的场景"
	    },
	    domProps: {
	      "value": _vm._s(_vm.otherSceneText)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.otherSceneText = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.otherSceneErrorTips))])]) : _vm._e(), _c('div', {
	    staticClass: "mainTitle"
	  }, [_vm._v("更关注的功能")]), _c('div', {
	    staticClass: "sceneArea"
	  }, [_vm._l((_vm.focusList), function(value, key) {
	    return _c('div', {
	      staticClass: "scene",
	      class: value.selected == true ? "active" : _vm.focusNum == 3 ? "none" : "",
	      style: (key % 4 == 0 ? "margin-right:0px;" : ""),
	      on: {
	        "click": function($event) {
	          _vm.selectFocus(key)
	        }
	      }
	    }, [_vm._v(_vm._s(value.name) + " ")])
	  }), _c('div', {
	    staticClass: "scene",
	    class: _vm.otherFocus ? "active" : _vm.focusNum == 3 ? "none" : "",
	    staticStyle: {
	      "margin-right": "0px"
	    },
	    on: {
	      "click": _vm.selectOtherFocus
	    }
	  }, [_vm._v("其它")])], 2), (_vm.otherFocus) ? _c('div', {
	    staticClass: "inputArea otherFocusInput"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.otherFocusText),
	      expression: "otherFocusText"
	    }],
	    attrs: {
	      "id": "otherFocusText",
	      "type": "text",
	      "placeholder": "请填写其他您关注的功能"
	    },
	    domProps: {
	      "value": _vm._s(_vm.otherFocusText)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.otherFocusText = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.otherFocusErrorTips))])]) : _vm._e(), _c('div', {
	    staticClass: "sendErrorTips"
	  }, [_vm._v(_vm._s(_vm.sendErrorTips))]), _c('div', {
	    staticClass: "sendBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    class: _vm.lock ? "lock" : "",
	    on: {
	      "click": _vm.send
	    }
	  }, [_vm._v(_vm._s(_vm.sendText))])])]) : _c('div', {
	    staticClass: "model successModel"
	  }, [_c('div', {
	    staticClass: "top"
	  }, [_c('span', [_vm._v("提交成功")]), (!_vm.show.noClose) ? _c('div', {
	    staticClass: "closeBtn",
	    on: {
	      "click": _vm.successAll
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })]) : _vm._e()]), _c('div', {
	    staticClass: "successDesc"
	  }, [_vm._v("您的资料将于1个工作日内完成审核，审核成功后将赠送您体验资格，请注意消息通知！")]), _c('div', {
	    staticClass: "sendBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    on: {
	      "click": _vm.successAll
	    }
	  }, [_vm._v("完成")])])])])
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "topDesc"
	  }, [_c('i', {
	    staticClass: "icon-star"
	  }), _c('span', [_vm._v("通过高级认证，将获得以下特权，享受更好的服务。")])])
	},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "topInfo"
	  }, [_c('div', {
	    staticClass: "topInfo-left"
	  }, [_c('img', {
	    attrs: {
	      "src": __webpack_require__(223)
	    }
	  }), _c('span', [_vm._v("7天免费体验企业版功能")])]), _c('div', {
	    staticClass: "topInfo-middle"
	  }, [_c('img', {
	    attrs: {
	      "src": __webpack_require__(224)
	    }
	  }), _c('span', [_vm._v("根据喜好推荐模版")])]), _c('div', {
	    staticClass: "topInfo-right"
	  }, [_c('img', {
	    attrs: {
	      "src": __webpack_require__(225)
	    }
	  }), _c('span', [_vm._v("专业的企业顾问")])])])
	},function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "mainTitle"
	  }, [_c('span', [_vm._v("营业执照")]), _c('span', [_vm._v("或其他证明文件")])])
	}]}

/***/ },
/* 223 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "73725635903a36d87cd8dac6065200d0.png";

/***/ },
/* 224 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "cea18fea7fd93a21d53bd1a9f9bfb4ec.png";

/***/ },
/* 225 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "4468be36c2730496e02c04882c59c893.png";

/***/ },
/* 226 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(227)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(230),
	  /* template */
	  __webpack_require__(231),
	  /* scopeId */
	  "data-v-15a0958e",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 227 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(228);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("6c26d5a6", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-15a0958e&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newMessageBox.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-15a0958e&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newMessageBox.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 228 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".newMessageBox[data-v-15a0958e]{top:62px;left:0;height:0;width:100%;position:fixed;z-index:30}.newMessageBox .container[data-v-15a0958e]{margin:auto}@media (min-width:0px){.newMessageBox .container[data-v-15a0958e]{width:980px}}@media (min-width:1400px){.newMessageBox .container[data-v-15a0958e]{width:1230px}}.newMessageBox .container .nsBox[data-v-15a0958e]{float:right;padding:27px 20px;line-height:1.4;box-shadow:0 6px 7px 0 rgba(25,25,25,.12);width:398px;height:68px;position:relative;-webkit-transform-origin:50% 0;transform-origin:50% 0;background:#fff;color:#484860;overflow:hidden}.newMessageBox .container .nsBox .icon[data-v-15a0958e]{position:absolute;display:block;top:28px;left:20px;width:38px;height:29px;background:url(" + __webpack_require__(229) + ") no-repeat}.newMessageBox .container .nsBox .data[data-v-15a0958e]{display:block;margin:2px 10px 10px 64px;font-family:PingFangSC-Regular;font-size:14px;color:#838383;line-height:22px;width:282px;text-decoration:none;text-align:left;cursor:pointer;height:66px;overflow:hidden}.newMessageBox .container .nsBox .data[data-v-15a0958e]:hover{color:#18ccc0}.newMessageBox .container .nsBox .close[data-v-15a0958e]{width:20px;height:20px;position:absolute;right:4px;top:4px;overflow:hidden;text-indent:100%;cursor:pointer;-webkit-backface-visibility:hidden;backface-visibility:hidden}.newMessageBox .container .nsBox .close[data-v-15a0958e]:focus,.newMessageBox .container .nsBox .close[data-v-15a0958e]:hover{outline:none}.newMessageBox .container .nsBox .close[data-v-15a0958e]:after,.newMessageBox .container .nsBox .close[data-v-15a0958e]:before{content:\"\";position:absolute;width:1px;height:65%;top:50%;left:50%;background:#6e6e6e}.newMessageBox .container .nsBox .close[data-v-15a0958e]:hover:after,.newMessageBox .container .nsBox .close[data-v-15a0958e]:hover:before{background:#545484}.newMessageBox .container .nsBox .close[data-v-15a0958e]:before{-webkit-transform:translate(-50%,-50%) rotate(45deg);transform:translate(-50%,-50%) rotate(45deg)}.newMessageBox .container .nsBox .close[data-v-15a0958e]:after{-webkit-transform:translate(-50%,-50%) rotate(-45deg);transform:translate(-50%,-50%) rotate(-45deg)}.newMessageBox .expand-enter[data-v-15a0958e]{display:block}.newMessageBox .expand-enter-active[data-v-15a0958e]{-webkit-animation:flipInX .8s;animation:flipInX .8s}.newMessageBox .expand-leave-active[data-v-15a0958e]{-webkit-animation:flipInX .8s reverse forwards;animation-name:flipInX .8s reverse forwards}@-moz-keyframes flipInX{0%{-webkit-transform:perspective(400px) rotateX(-90deg);transform:perspective(400px) rotateX(-90deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in}40%{-webkit-transform:perspective(400px) rotateX(20deg);transform:perspective(400px) rotateX(20deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}60%{-webkit-transform:perspective(400px) rotateX(-10deg);transform:perspective(400px) rotateX(-10deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in;opacity:1}80%{-webkit-transform:perspective(400px) rotateX(5deg);transform:perspective(400px) rotateX(5deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}to{-webkit-transform:perspective(400px);transform:perspective(400px)}}@-webkit-keyframes flipInX{0%{-webkit-transform:perspective(400px) rotateX(-90deg);transform:perspective(400px) rotateX(-90deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in}40%{-webkit-transform:perspective(400px) rotateX(20deg);transform:perspective(400px) rotateX(20deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}60%{-webkit-transform:perspective(400px) rotateX(-10deg);transform:perspective(400px) rotateX(-10deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in;opacity:1}80%{-webkit-transform:perspective(400px) rotateX(5deg);transform:perspective(400px) rotateX(5deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}to{-webkit-transform:perspective(400px);transform:perspective(400px)}}@-o-keyframes flipInX{0%{-webkit-transform:perspective(400px) rotateX(-90deg);transform:perspective(400px) rotateX(-90deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in}40%{-webkit-transform:perspective(400px) rotateX(20deg);transform:perspective(400px) rotateX(20deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}60%{-webkit-transform:perspective(400px) rotateX(-10deg);transform:perspective(400px) rotateX(-10deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in;opacity:1}80%{-webkit-transform:perspective(400px) rotateX(5deg);transform:perspective(400px) rotateX(5deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}to{-webkit-transform:perspective(400px);transform:perspective(400px)}}@keyframes flipInX{0%{-webkit-transform:perspective(400px) rotateX(-90deg);transform:perspective(400px) rotateX(-90deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in}40%{-webkit-transform:perspective(400px) rotateX(20deg);transform:perspective(400px) rotateX(20deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}60%{-webkit-transform:perspective(400px) rotateX(-10deg);transform:perspective(400px) rotateX(-10deg);-webkit-transition-timing-function:ease-in;transition-timing-function:ease-in;opacity:1}80%{-webkit-transform:perspective(400px) rotateX(5deg);transform:perspective(400px) rotateX(5deg);-webkit-transition-timing-function:ease-out;transition-timing-function:ease-out}to{-webkit-transform:perspective(400px);transform:perspective(400px)}}", ""]);

	// exports


/***/ },
/* 229 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "6120d5390a418b28ad6c5dada443ae05.png";

/***/ },
/* 230 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	exports.default = {
	  data: function data() {
	    return {
	      newsData: {},
	      isNewmsgBoxShow: null,
	      newsContent: ""
	    };
	  },

	  methods: {
	    boxClose: function boxClose() {
	      if (this.isNewmsgBoxShow) {
	        this.isNewmsgBoxShow = false;
	      }
	    },
	    sendPointData: function sendPointData() {
	      var self = this;
	      //点击提交点击信息
	      $.ajax({
	        url: _config2.default.API_MESSAGE_MARKING,
	        type: 'get',
	        dataType: 'json',
	        data: { mid: self.newsData.data.dataList.id },
	        success: function success(result) {
	          // console.log("link send ok");
	          self.isNewmsgBoxShow = false;
	        }
	      });
	    },
	    fixContent: function fixContent(content) {
	      this.newsContent = content.replace(/`/g, "<br>");
	    }
	  },
	  mounted: function mounted() {
	    var self = this;

	    var cookie = _utils2.default.getCookie('token');
	    var popFlag = _utils2.default.getCookie(_utils2.default.getCookie('Makauid') + '_popFlag');
	    //如果没有flag的话添加一个
	    if (popFlag == null) {
	      _utils2.default.setCookie(_utils2.default.getCookie('Makauid') + '_popFlag', 1, 2 * 60 * 60 * 1000);
	      //cookie定时2小时
	      popFlag = 1;
	    }

	    //在登录成功并且第一次看弹出弹窗，弹出后设置已弹
	    if (cookie && popFlag == 1) {

	      $.ajax({
	        url: _config2.default.API_MESSAGE_POPS,
	        type: 'GET',
	        dataType: 'json',
	        success: function success(result) {
	          if (result.code === 200 && result.data.dataList.content) {
	            self.newsData = result;
	            self.fixContent(result.data.dataList.content);
	            self.isNewmsgBoxShow = true;
	            _utils2.default.setCookie(_utils2.default.getCookie('Makauid') + '_popFlag', 0, 2 * 60 * 60 * 1000);
	            //cookie定时2小时
	          }
	        }
	      });
	    }
	  }
	};

/***/ },
/* 231 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "newMessageBox"
	  }, [_c('div', {
	    staticClass: "container"
	  }, [_c('transition', {
	    attrs: {
	      "name": "expand"
	    }
	  }, [(_vm.isNewmsgBoxShow) ? _c('div', {
	    staticClass: "nsBox"
	  }, [_c('em', {
	    staticClass: "icon"
	  }), _c('a', {
	    staticClass: "data",
	    attrs: {
	      "target": "_blank",
	      "href": _vm.newsData.data.dataList.link
	    },
	    on: {
	      "click": function($event) {
	        _vm.sendPointData()
	      }
	    }
	  }, [_vm._v(_vm._s(_vm.newsContent))]), _c('span', {
	    staticClass: "close",
	    on: {
	      "click": function($event) {
	        _vm.boxClose()
	      }
	    }
	  })]) : _vm._e()])], 1)])
	},staticRenderFns: []}

/***/ },
/* 232 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('section', {
	    staticClass: "des-login"
	  }, [_c('transition', {
	    attrs: {
	      "name": "fade"
	    }
	  }, [(_vm.show.isShow) ? _c('div', {
	    staticClass: "des-login-page"
	  }, [_c('div', {
	    staticClass: "des-login-background"
	  }), _vm._v(" "), _c('transition', {
	    attrs: {
	      "name": "scale"
	    }
	  }, [(_vm.show.isShow) ? _c('div', {
	    staticClass: "des-login-container",
	    class: {
	      'des-sign-container': !_vm.show.isLogin
	    }
	  }, [_c('div', {
	    staticClass: "top"
	  }, [(_vm.canClose) ? _c('div', {
	    staticClass: "closeBtn",
	    on: {
	      "click": _vm.close
	    }
	  }, [_c('i', {
	    staticClass: "icon-ic_close"
	  })]) : _vm._e(), _vm._v(" "), _c('div', {
	    staticClass: "loginNav",
	    class: {
	      'navActive': _vm.show.isLogin
	    },
	    on: {
	      "click": function($event) {
	        _vm.changeState(true)
	      }
	    }
	  }, [_vm._v("登录")]), _vm._v(" "), _c('div', {
	    staticClass: "regNav",
	    class: {
	      'navActive': !_vm.show.isLogin
	    },
	    on: {
	      "click": function($event) {
	        _vm.changeState(false)
	      }
	    }
	  }, [_vm._v("注册")])]), _vm._v(" "), (_vm.show.isLogin) ? _c('div', {
	    staticClass: "loginArea"
	  }, [_c('form', {
	    staticClass: "loginForm"
	  }, [_c('div', {
	    staticClass: "account inputArea"
	  }, [_c('i', {
	    staticClass: "phoneIcon icon-phonecall"
	  }), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.accountLogin),
	      expression: "accountLogin"
	    }],
	    attrs: {
	      "id": "accountLogin",
	      "type": "text",
	      "placeholder": "手机号/邮箱"
	    },
	    domProps: {
	      "value": _vm._s(_vm.accountLogin)
	    },
	    on: {
	      "focus": function($event) {
	        _vm.clearErrorTip("accountLogin")
	      },
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.accountLogin = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.accountLoginErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "password inputArea"
	  }, [_c('i', {
	    staticClass: "passwordIcon icon-pastword"
	  }), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.passwordLogin),
	      expression: "passwordLogin"
	    }],
	    attrs: {
	      "id": "passwordLogin",
	      "type": "password",
	      "placeholder": "密码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.passwordLogin)
	    },
	    on: {
	      "focus": function($event) {
	        _vm.clearErrorTip("passwordLogin")
	      },
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.passwordLogin = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.passwordLoginErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "loginErrorTips"
	  }, [_vm._v(_vm._s(_vm.loginErrorTips))]), _vm._v(" "), _c('div', {
	    staticClass: "loginBtn"
	  }, [_c('button', {
	    staticClass: "btn",
	    attrs: {
	      "type": "submit"
	    },
	    on: {
	      "click": _vm.toLogin
	    }
	  }, [_vm._v("立即登录")])])]), _vm._v(" "), _c('div', {
	    staticClass: "forgetPassWord"
	  }, [_c('span', {
	    on: {
	      "click": _vm.showForgetPassword
	    }
	  }, [_vm._v("忘记密码?")])]), _vm._v(" "), _c('div', {
	    staticClass: "thirdLogin"
	  }, [_c('div', {
	    staticClass: "thirdLoginBtn qq",
	    on: {
	      "click": function($event) {
	        _vm.thirdLogin("qq")
	      }
	    }
	  }, [_c('i', {
	    staticClass: "icon-qq"
	  }), _vm._v(" "), _c('span', [_vm._v("QQ登录")])]), _vm._v(" "), _c('div', {
	    staticClass: "thirdLoginBtn wx",
	    on: {
	      "click": function($event) {
	        _vm.thirdLogin("weixin")
	      }
	    }
	  }, [_c('i', {
	    staticClass: "icon-weixin"
	  }), _vm._v(" "), _c('span', [_vm._v("微信登录")])]), _vm._v(" "), _c('div', {
	    staticClass: "thirdLoginBtn sina",
	    on: {
	      "click": function($event) {
	        _vm.thirdLogin("weibo")
	      }
	    }
	  }, [_c('i', {
	    staticClass: "icon-sina"
	  }), _vm._v(" "), _c('span', [_vm._v("新浪登录")])])])]) : _c('div', {
	    staticClass: "regArea"
	  }, [_c('div', {
	    staticClass: "tel inputArea"
	  }, [_c('i', {
	    staticClass: "phoneIcon icon-phonecall"
	  }), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.accountReg),
	      expression: "accountReg"
	    }],
	    attrs: {
	      "id": "accountReg",
	      "type": "text",
	      "name": "accountReg",
	      "placeholder": "手机号码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.accountReg)
	    },
	    on: {
	      "focus": function($event) {
	        _vm.clearErrorTip("accountReg")
	      },
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.accountReg = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.accountRegErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "verifyCode inputArea"
	  }, [_c('i', {
	    staticClass: "codeIcon icon-code"
	  }), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.verifyCode),
	      expression: "verifyCode"
	    }],
	    attrs: {
	      "id": "verifyCode",
	      "type": "text",
	      "placeholder": "验证码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.verifyCode)
	    },
	    on: {
	      "focus": function($event) {
	        _vm.clearErrorTip("verifyCode")
	      },
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.verifyCode = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('div', {
	    staticClass: "verifyCodeBtn",
	    class: {
	      'disable': _vm.counting || !_vm.accountReg
	    },
	    on: {
	      "click": _vm.getVerifyCode
	    }
	  }, [_vm._v(_vm._s(_vm.getcode))]), _vm._v(" "), _c('div', {
	    staticClass: "errorTips",
	    staticStyle: {
	      "right": "165px"
	    }
	  }, [_vm._v(_vm._s(_vm.verifyCodeErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "passwordReg inputArea"
	  }, [_c('i', {
	    staticClass: "passwordIcon icon-pastword",
	    staticStyle: {
	      "line-height": "64px"
	    }
	  }), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.passwordReg),
	      expression: "passwordReg"
	    }],
	    attrs: {
	      "id": "passwordReg",
	      "type": "password",
	      "placeholder": "请输入6-20位密码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.passwordReg)
	    },
	    on: {
	      "focus": function($event) {
	        _vm.clearErrorTip("passwordReg")
	      },
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.passwordReg = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('div', {
	    staticClass: "errorTips",
	    staticStyle: {
	      "top": "17px"
	    }
	  }, [_vm._v(_vm._s(_vm.passwordRegErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "passwordAgain inputArea"
	  }, [_c('i', {
	    staticClass: "passwordIcon icon-pastword"
	  }), _vm._v(" "), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.passwordAgain),
	      expression: "passwordAgain"
	    }],
	    attrs: {
	      "id": "passwordAgain",
	      "type": "password",
	      "placeholder": "确认密码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.passwordAgain)
	    },
	    on: {
	      "focus": function($event) {
	        _vm.clearErrorTip("passwordAgain")
	      },
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.passwordAgain = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('div', {
	    staticClass: "errorTips"
	  }, [_vm._v(_vm._s(_vm.passwordAgainErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "reg-check"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.regCheck),
	      expression: "regCheck"
	    }],
	    attrs: {
	      "type": "checkbox"
	    },
	    domProps: {
	      "checked": Array.isArray(_vm.regCheck) ? _vm._i(_vm.regCheck, null) > -1 : (_vm.regCheck)
	    },
	    on: {
	      "click": function($event) {
	        var $$a = _vm.regCheck,
	          $$el = $event.target,
	          $$c = $$el.checked ? (true) : (false);
	        if (Array.isArray($$a)) {
	          var $$v = null,
	            $$i = _vm._i($$a, $$v);
	          if ($$c) {
	            $$i < 0 && (_vm.regCheck = $$a.concat($$v))
	          } else {
	            $$i > -1 && (_vm.regCheck = $$a.slice(0, $$i).concat($$a.slice($$i + 1)))
	          }
	        } else {
	          _vm.regCheck = $$c
	        }
	      }
	    }
	  }), _vm._v(" "), _c('span', [_vm._v("同意")]), _vm._v(" "), _c('a', {
	    attrs: {
	      "href": "http://res.maka.im/common/files/MAKA%E6%9C%8D%E5%8A%A1%E6%9D%A1%E6%AC%BE20170316.pdf",
	      "target": "_blank"
	    }
	  }, [_vm._v("用户服务协议")]), _vm._v(" "), _c('span', {
	    staticClass: "regErrorTips"
	  }, [_vm._v(_vm._s(_vm.regErrorTips))])]), _vm._v(" "), _c('div', {
	    staticClass: "regBtn"
	  }, [_c('div', {
	    staticClass: "btn",
	    on: {
	      "click": _vm.toReg
	    }
	  }, [_vm._v(_vm._s(_vm.signup))])])])]) : _vm._e()]), _vm._v(" "), (_vm.needVerify) ? _c('div', {
	    staticClass: "phone-verify-box"
	  }, [_c('div', {
	    staticClass: "phone-verify-title"
	  }, [_vm._v("请输入验证码，继续获取")]), _vm._v(" "), _c('div', {
	    staticClass: "phone-verify-content"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.phoneVerifyCode),
	      expression: "phoneVerifyCode"
	    }],
	    staticClass: "phone-input",
	    attrs: {
	      "type": "text"
	    },
	    domProps: {
	      "value": _vm._s(_vm.phoneVerifyCode)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.phoneVerifyCode = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('span', [_c('img', {
	    staticClass: "phone-verify-img",
	    attrs: {
	      "src": _vm.phoneVerifyImg
	    }
	  })])]), _vm._v(" "), _c('div', {
	    staticClass: "phone-verify-button",
	    on: {
	      "click": _vm.pushPhoneVerify
	    }
	  }, [_vm._v("提交")])]) : _vm._e()], 1) : _vm._e()]), _vm._v(" "), _c('forget-passWord', {
	    attrs: {
	      "show": _vm.show
	    }
	  }), _vm._v(" "), _c('new-identify-model', {
	    attrs: {
	      "show": _vm.show
	    },
	    on: {
	      "closeAll": _vm.close
	    }
	  }), _vm._v(" "), (_vm.show.primaryIdentify) ? _c('primary-identify-model', {
	    attrs: {
	      "show": _vm.show
	    },
	    on: {
	      "closeAll": _vm.close
	    }
	  }) : _vm._e(), _vm._v(" "), (_vm.show.seniorIdentity) ? _c('senior-identity-model', {
	    attrs: {
	      "show": _vm.show
	    },
	    on: {
	      "closeAll": _vm.close
	    }
	  }) : _vm._e(), _vm._v(" "), _c('new-message-box')], 1)
	},staticRenderFns: []}

/***/ },
/* 233 */,
/* 234 */,
/* 235 */
/***/ function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ },
/* 236 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _events = __webpack_require__(237);

	var _events2 = _interopRequireDefault(_events);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	function initLogin() {
	  var makaUid = _utils2.default.getCookie('Makauid');
	  var token = _utils2.default.getCookie('token');

	  if (token) {
	    setLoginState();
	    $.ajax({
	      url: _config2.default.API_USERINFO_UPLOAD + makaUid,
	      type: 'GET',
	      dataType: 'json',
	      data: {},
	      success: function success(result) {
	        if (result.code == 200) {
	          window.USER_INFO = result.data;
	          setUserImage(result.data.thumb);
	        }
	      }
	    });
	  } else {
	    var myProjectButton = $('.nav')[0];
	    myProjectButton.classList.add('des-hidden');
	  }
	}

	function setLoginState() {
	  var userLogin = $('.login')[0];
	  userLogin.classList.remove('no-login');
	  userLogin.classList.add('has-login');
	  // let myProjectButton = $('.nav')[0]
	  // myProjectButton.classList.remove('des-hidden')
	}

	function setUserImage(img) {
	  var userImage = $('.user-image')[0];
	  userImage.src = img;
	}

	window.onload = initLogin();

	window.logout = function () {
	  _utils2.default.deleteCookie('token');
	  _utils2.default.deleteCookie('Makauid');
	  _utils2.default.clearCookie();
	  location.reload();
	  // window.location.href = '/'
	};

	window.openLogin = needLogin;
	window.openSign = openSign;

	function needLogin() {
	  if (window.loginCompontent && !window.loginCompontent.show.isShow) {
	    window.loginCompontent.show.isLogin = true;
	    window.loginCompontent.show.isShow = true;
	  }
	}

	function openSign() {
	  if (window.loginCompontent && !window.loginCompontent.show.isShow) {
	    window.loginCompontent.show.isLogin = false;
	    window.loginCompontent.show.isShow = true;
	  }
	}

/***/ },
/* 237 */
/***/ function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	var EventListener = {
	  /**
	   * Listen to DOM events during the bubble phase.
	   *
	   * @param {DOMEventTarget} target DOM element to register listener on.
	   * @param {string} eventType Event type, e.g. 'click' or 'mouseover'.
	   * @param {function} callback Callback function.
	   * @return {object} Object with a `remove` method.
	   */
	  listen: function listen(target, eventType, callback) {
	    if (target.addEventListener) {
	      target.addEventListener(eventType, callback, false);
	      return {
	        remove: function remove() {
	          target.removeEventListener(eventType, callback, false);
	        }
	      };
	    } else if (target.attachEvent) {
	      target.attachEvent('on' + eventType, callback);
	      return {
	        remove: function remove() {
	          target.detachEvent('on' + eventType, callback);
	        }
	      };
	    }
	  }
	};

	exports.default = EventListener;

/***/ },
/* 238 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _global = __webpack_require__(239);

	var _global2 = _interopRequireDefault(_global);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	var hasClassName = 'des-hasfollow';
	var noClassName = 'des-unfollow';

	window.followDesigner = function (e) {
	  var makaUid = _utils2.default.getCookie('token');
	  if (!makaUid) {
	    // needLogin()
	    _global2.default.openLogin();
	    return false;
	  }
	  var did = e.dataset.designerid;
	  var parenNode = e.parentNode;

	  $.ajax({
	    url: _config2.default.API_DESIFNER_FOLLOW_ADD + did,
	    type: 'POST',
	    dataType: 'json',
	    data: {},
	    success: function success(result) {
	      if (result.code === 200) {
	        parenNode.classList.remove(noClassName);
	        parenNode.classList.add(hasClassName);
	      }
	    }
	  });
	};

	window.unFollowDesigner = function (e) {
	  var did = e.dataset.designerid;
	  var parenNode = e.parentNode;

	  var makaUid = _utils2.default.getCookie('Makauid');
	  if (!makaUid) {
	    needLogin();
	    return false;
	  }

	  $.ajax({
	    url: _config2.default.API_DESIFNER_FOLLOW_DEL + did,
	    type: 'PUT',
	    dataType: 'json',
	    data: {},
	    success: function success(result) {
	      if (result.code === 200) {
	        parenNode.classList.remove(hasClassName);
	        parenNode.classList.add(noClassName);
	      }
	    }
	  });
	};

/***/ },
/* 239 */
/***/ function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	exports.default = {
	  openLogin: function openLogin() {
	    if (window.loginCompontent && !window.loginCompontent.show.isShow) {
	      window.loginCompontent.show.isLogin = true;
	      window.loginCompontent.show.isShow = true;
	    }
	  }
	};

/***/ },
/* 240 */,
/* 241 */,
/* 242 */,
/* 243 */,
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */,
/* 249 */,
/* 250 */,
/* 251 */,
/* 252 */,
/* 253 */,
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(260)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(262),
	  /* template */
	  __webpack_require__(280),
	  /* scopeId */
	  "data-v-92bc197a",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 260 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(261);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("0f04faf3", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-92bc197a&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./templatePay.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-92bc197a&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./templatePay.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 261 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".des-template-pay[data-v-92bc197a]{position:relative;margin-top:20px}.des-template-pay .des-buy-price[data-v-92bc197a]{font-size:16px;color:#4a4a4a}.des-template-pay .des-button[data-v-92bc197a]{background:#18ccc0;border:1px solid #1ec9be;border-radius:3px;color:#fff;font-size:16px;text-align:center;cursor:pointer;height:35px;line-height:35px}.des-template-pay .des-button[data-v-92bc197a]:hover{opacity:.8}.des-template-pay .des-buy-button[data-v-92bc197a]{margin-top:11px}.des-template-pay .des-buy-button2[data-v-92bc197a]{position:absolute;height:40px;width:186px;top:31px;line-height:40px}.des-template-pay .des-pay[data-v-92bc197a]{position:relative;width:186px;margin-top:10px;visibility:hidden;overflow:hidden;border:1px solid #f0f0f0}.des-template-pay .des-pay .des-form[data-v-92bc197a]{height:0;margin:0;padding:0;font-size:14px;border-top:3px solid #18ccc0;will-change:height;transition:height .3s ease}.des-template-pay .des-pay .des-form .des-radio[data-v-92bc197a]{height:40px;padding:0 5px;border-top:1px solid #f0f0f0;display:flex;align-items:center;color:#000}.des-template-pay .des-pay .des-form .des-radio .des-radio-notes[data-v-92bc197a]{font-size:10px;color:#aaa;padding-left:10px}.des-template-pay .des-pay .des-form .des-radio .des-radio-label[data-v-92bc197a]{margin-left:5px;max-width:150px;cursor:pointer}.des-template-pay .des-pay .des-form .des-radio .des-pay-price[data-v-92bc197a]{font-size:18px;color:#f5a623;margin-left:10px}.des-template-pay .des-pay .des-form .des-radio .un-choics[data-v-92bc197a]{color:#999}.des-template-pay .des-pay .des-form .des-radio .des-radio-nowarp[data-v-92bc197a]{white-space:nowrap}.des-template-pay .des-pay .des-form .des-radio .des-less-tips[data-v-92bc197a]{margin-left:6px;color:#4a4a4a;font-size:12px;line-height:14px;font-weight:600;margin-top:3px;text-align:justify}.des-template-pay .des-pay .des-form .des-radio .des-less-tips a[data-v-92bc197a]{text-decoration:none;display:inline;color:#18ccc0;font-size:12px;line-height:14px;cursor:pointer}.des-template-pay .des-pay .des-form .des-radio input[type=radio][data-v-92bc197a]{-webkit-appearance:none;width:14px;height:14px;border-radius:50%;border:1px solid #b3b7c1;outline:none}.des-template-pay .des-pay .des-form .des-radio input[type=radio][data-v-92bc197a]:checked{border:1px solid #18ccc0}.des-template-pay .des-pay .des-form .des-radio input[type=radio][data-v-92bc197a]:before{content:\"\";display:block;width:52%;height:52%;margin:25%;border-radius:50%}.des-template-pay .des-pay .des-form .des-radio input[type=radio][data-v-92bc197a]:checked:before{background:#18ccc0}.des-template-pay .des-pay .des-pay-button[data-v-92bc197a]{height:40px;width:186px;line-height:40px}.des-paying .des-buy-button2[data-v-92bc197a],.des-paying .des-buy-price[data-v-92bc197a]{display:none}.des-paying .des-pay[data-v-92bc197a]{visibility:visible}.des-paying .des-pay .des-form[data-v-92bc197a]{height:165px}", ""]);

	// exports


/***/ },
/* 262 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _postopen = __webpack_require__(263);

	var _postopen2 = _interopRequireDefault(_postopen);

	var _global = __webpack_require__(239);

	var _global2 = _interopRequireDefault(_global);

	var _tipModule = __webpack_require__(264);

	var _tipModule2 = _interopRequireDefault(_tipModule);

	var _tipsBar = __webpack_require__(269);

	var _tipsBar2 = _interopRequireDefault(_tipsBar);

	var _rechargeModal = __webpack_require__(274);

	var _rechargeModal2 = _interopRequireDefault(_rechargeModal);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	  name: 'template-pay',
	  components: {
	    'tip-module': _tipModule2.default,
	    'tips-bar': _tipsBar2.default,
	    'recharge-modal': _rechargeModal2.default
	  },
	  data: function data() {
	    return {
	      price: window.TEMPLATE_INFO ? window.TEMPLATE_INFO.price : 0,
	      hasTemplate: window.TEMPLATE_INFO.is_user_template,
	      balance: 0,
	      isBuy: false,
	      payway: 'balance',
	      rechargeShow: {
	        rechargeShow: false
	      },
	      tipInfo: {
	        show: false
	      },
	      tipsBarInfo: {
	        show: false,
	        type: 'success'
	      },
	      couponInfo: {
	        template: 3,
	        fullcut: false
	      },
	      hasAdmin: false,
	      businessInfo: {}
	    };
	  },
	  computed: {},
	  mounted: function mounted() {
	    this.judgeCouponPay(window.TEMPLATE_INFO.price);
	    if (!_utils2.default.needLogin()) {
	      this.getBusinessInfo();
	    }
	  },

	  methods: {
	    buyTemplate: function buyTemplate() {
	      if (_utils2.default.needLogin()) {
	        // 打开登录窗口
	        _global2.default.openLogin();
	        return;
	      }
	      this.balance = window.USER_INFO ? window.USER_INFO.acountBalance : 0;
	      if (this.balance < this.price) {
	        this.payway = 'alipay';
	      }
	      this.isBuy = true;
	    },
	    payMoney: function payMoney(e) {
	      var self = this;
	      var templateinfo = window.TEMPLATE_INFO;
	      var userinfo = window.USER_INFO;
	      var type = this.payway;
	      var price = this.price;
	      var ele = e.target;
	      var is_use_coupon = 0;

	      if (type === 'alipay') {
	        (0, _postopen2.default)(_config2.default.API_BUYTEMPLATEBYALI + templateinfo.id, { userId: userinfo.uid, paytype: type });
	        this.tipInfo = {
	          show: true,
	          tipModel: {
	            title: '支付成功了吗？',
	            content: '收款人为maka.im',
	            successName: '支付成功',
	            failName: '未成功，再来一次',
	            successCallback: function successCallback() {
	              location.reload();
	            }
	          }
	        };
	      } else {
	        if (type === 'balance' || type === 'template_coupon') {

	          $(ele).css({ 'pointer-events': "none", 'background': "#aaaaaa" });

	          if (type === 'template_coupon') {
	            is_use_coupon = 1;
	          }

	          $.ajax({
	            url: _config2.default.API_BUYTEMPLATE + templateinfo.id,
	            type: 'POST',
	            dataType: 'json',
	            data: {
	              payType: type,
	              is_use_coupon: is_use_coupon
	            },
	            success: function success(data) {
	              if (data.code === 200) {
	                self.tipsBarInfo = {
	                  show: true,
	                  type: 'success',
	                  msg: '购买成功'
	                };
	                self.hasTemplate = true;
	                if (type === 'balance' && window.USER_INFO) {
	                  window.USER_INFO.acountBalance -= price;
	                }
	              } else {
	                self.tipsBarInfo = {
	                  show: true,
	                  type: 'fail',
	                  msg: data.message
	                };
	                $(ele).css({ 'pointer-events': "auto", 'background': "#18CCC0" });
	              }
	            },
	            error: function error() {
	              self.tipsBarInfo = {
	                show: true,
	                type: 'fail',
	                msg: '购买失败'
	              };
	              $(ele).css({ 'pointer-events': "auto", 'background': "#18CCC0" });
	            }
	          });
	        }
	      }
	    },
	    useTemplate: function useTemplate() {
	      var templateinfo = window.TEMPLATE_INFO;
	      var source = _utils2.default.getParam('source');

	      if (source === 'desktop') {
	        (0, _postopen2.default)(_config2.default.API_USETEMPLATE + templateinfo.id, {});
	      } else {
	        if (_utils2.default.needLogin()) {
	          if (window.loginCompontent && !window.loginCompontent.show.isShow) {
	            window.loginCompontent.show.isLogin = true;
	            window.loginCompontent.show.isShow = true;
	          }
	        } else {
	          window.open(_config2.default.API_V4_USETEMPLATE + templateinfo.id + '?type=use');
	        }
	      }
	    },
	    judgeCouponPay: function judgeCouponPay(tp_price) {
	      var self = this;
	      $.ajax({
	        url: _config2.default.API_USERCOUPON,
	        type: 'GET',
	        dataType: 'json',
	        data: { type: 'payinfo', money: tp_price },
	        success: function success(data) {
	          if (data.code === 200) {
	            self.couponInfo.template = data.data.template;
	            self.couponInfo.fullcut = data.data.fullcut;
	          } else {
	            console.log('Server Error tips>>> ', data.data);
	          }
	        },
	        error: function error() {
	          console.log('模版券信息获取失败！');
	        }
	      });
	    },
	    showRecharg: function showRecharg() {
	      this.rechargeShow.rechargeShow = true;
	    },
	    getBusinessInfo: function getBusinessInfo() {
	      var self = this;
	      $.ajax({
	        url: _config2.default.API_BUSINESS_INFO,
	        type: 'GET',
	        dataType: 'json',
	        success: function success(result) {
	          if (result.data) {
	            if (result.data.firstName) {
	              self.hasAdmin = true;
	            }
	            self.businessInfo = result.data;
	          }
	        }
	      });
	    }
	  }
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ },
/* 263 */
/***/ function(module, exports) {

	"use strict";

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	function postopen(URL, PARAMS) {
	  var temp_form = document.createElement("form");
	  // var temp_form = document.getElementById('aliForm');
	  temp_form.action = URL;
	  temp_form.target = "_blank";
	  temp_form.method = "post";
	  temp_form.style.display = "none";for (var x in PARAMS) {
	    var opt = document.createElement("textarea");
	    opt.name = x;
	    opt.value = PARAMS[x];
	    temp_form.appendChild(opt);
	  }
	  document.body.appendChild(temp_form);
	  temp_form.submit();
	}

	exports.default = postopen;

/***/ },
/* 264 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(265)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(267),
	  /* template */
	  __webpack_require__(268),
	  /* scopeId */
	  "data-v-ee2539c8",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 265 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(266);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("722a2948", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-ee2539c8&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./tipModule.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-ee2539c8&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./tipModule.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 266 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".des-tip .des-tip-background[data-v-ee2539c8]{position:fixed;height:100%;width:100%;top:0;left:0;background:rgba(0,0,0,.8);z-index:1000}.des-tip .des-tip-container[data-v-ee2539c8]{position:fixed;top:250px;left:0;right:0;margin:auto;width:600px;height:150px;padding:20px;background:#fff;z-index:1001;border:1px solid silver}.des-tip .des-tip-container .des-tip-header[data-v-ee2539c8]{position:relative;width:100%;padding:0 0 8px;line-height:30px;color:#414141;font-size:16px;font-weight:600;border-bottom:1px solid #d6d6d6}.des-tip .des-tip-container .des-tip-header .des-tip-close[data-v-ee2539c8]{position:absolute;top:0;right:0;cursor:pointer;color:#999}.des-tip .des-tip-container .des-tip-body[data-v-ee2539c8]{margin-top:10px;width:100%}.des-tip .des-tip-container .des-tip-body .des-tip-content[data-v-ee2539c8]{font-size:14px;line-height:30px;height:67px;color:#83817b}.des-tip .des-tip-container .des-tip-body .des-tip-option[data-v-ee2539c8]{display:flex;justify-content:flex-end}.des-tip .des-tip-container .des-tip-body .des-tip-option .des-tip-button[data-v-ee2539c8]{padding:0 10px;height:35px;line-height:35px;min-width:100px;margin-left:15px;border:1px solid #fff;border-radius:3px;text-align:center;cursor:pointer;color:#fff}.des-tip .des-tip-container .des-tip-body .des-tip-option .add[data-v-ee2539c8]{background:#99c432}.des-tip .des-tip-container .des-tip-body .des-tip-option .del[data-v-ee2539c8]{color:#ababab;background:#eee}.fade-enter-active[data-v-ee2539c8],.fade-leave-active[data-v-ee2539c8]{transition:transform .2s ease;transform:translateY(0)}.fade-enter[data-v-ee2539c8],.fade-leave-active[data-v-ee2539c8]{transform:translateY(-50px)}", ""]);

	// exports


/***/ },
/* 267 */
/***/ function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	// tipModel props 数据结构
	// {
	//   show: true,
	//   tipModel: {
	//     title: '支付成功了吗？',
	//     content: '收款人为maka.im',
	//     successName: '支付成功',
	//     failName: '未成功，再来一次',
	//     successCallback: () => {},
	//     failCallback: () => {}
	//   }
	// }

	exports.default = {
	  name: 'tip-module',
	  props: ['tipInfo'],
	  data: function data() {
	    return {
	      show: false,
	      tipModel: {}
	    };
	  },
	  computed: {},
	  watch: {
	    tipInfo: function tipInfo(newTipInfo) {
	      if (newTipInfo.show) {
	        this.show = true;
	        this.tipModel = newTipInfo.tipModel;
	      }
	    }
	  },
	  created: function created() {},
	  mounted: function mounted() {},

	  methods: {
	    closeModule: function closeModule() {
	      this.show = false;
	    },
	    successFun: function successFun() {
	      if (this.tipModel.successCallback) {
	        this.tipModel.successCallback();
	      }
	      this.show = false;
	    },
	    failFun: function failFun() {
	      if (this.tipModel.failCallback) {
	        this.tipModel.failCallback();
	      }
	      this.show = false;
	    }
	  }
	};

/***/ },
/* 268 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "des-tip"
	  }, [(_vm.show) ? _c('div', {
	    staticClass: "des-tip-background"
	  }) : _vm._e(), _vm._v(" "), _c('transition', {
	    attrs: {
	      "name": "fade"
	    }
	  }, [(_vm.show) ? _c('div', {
	    staticClass: "des-tip-container"
	  }, [_c('div', {
	    staticClass: "des-tip-header"
	  }, [_c('div', {
	    staticClass: "des-tip-title"
	  }, [_vm._v(_vm._s(_vm.tipModel.title || '提示'))]), _vm._v(" "), _c('div', {
	    staticClass: "des-tip-close"
	  }, [_c('i', {
	    staticClass: "icon-ic_close",
	    on: {
	      "click": _vm.closeModule
	    }
	  })])]), _vm._v(" "), _c('div', {
	    staticClass: "des-tip-body"
	  }, [_c('div', {
	    staticClass: "des-tip-content"
	  }, [_vm._v(_vm._s(_vm.tipModel.content))]), _vm._v(" "), _c('div', {
	    staticClass: "des-tip-option"
	  }, [_c('div', {
	    staticClass: "des-tip-button add",
	    on: {
	      "click": _vm.successFun
	    }
	  }, [_vm._v(_vm._s(_vm.tipModel.successName || '确定'))]), _vm._v(" "), _c('div', {
	    staticClass: "des-tip-button del",
	    on: {
	      "click": _vm.failFun
	    }
	  }, [_vm._v(_vm._s(_vm.tipModel.failName || '取消'))])])])]) : _vm._e()])], 1)
	},staticRenderFns: []}

/***/ },
/* 269 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(270)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(272),
	  /* template */
	  __webpack_require__(273),
	  /* scopeId */
	  "data-v-babee1a0",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 270 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(271);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("af8eff96", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-babee1a0&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./tipsBar.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-babee1a0&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./tipsBar.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 271 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".des-tips-bar[data-v-babee1a0]{position:fixed;top:60px;left:0;right:0;height:48px;width:100%;z-index:99;display:flex;justify-content:center;transform:translateY(0)}.des-tips-bar-container[data-v-babee1a0]{height:48px;line-height:48px;padding:0 20px;display:flex;background:rgba(0,0,0,.5);border-radius:3px}.des-tips-bar-container i[data-v-babee1a0]{margin-right:18px;line-height:48px;font-size:18px}.des-tips-bar-container span[data-v-babee1a0]{color:#fff}.des-tips-bar-container .success[data-v-babee1a0]{color:#b3ec11}.des-tips-bar-container .fail[data-v-babee1a0]{color:#f67171}.des-tips-bar-container .prompt[data-v-babee1a0]{color:#ffc131}.fade-enter-active[data-v-babee1a0],.fade-leave-active[data-v-babee1a0]{transition:transform .3s ease}.fade-enter[data-v-babee1a0],.fade-leave-active[data-v-babee1a0]{transform:translateY(-50px)}", ""]);

	// exports


/***/ },
/* 272 */
/***/ function(module, exports) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

	// tipsBar props 数据结构
	// {
	//   show: true,
	//   type: 'success',  // or 'fail', 'prompt'
	//   msg: '购买成功'
	// }

	exports.default = {
	  name: 'tips-bar',
	  props: ['tipInfo'],
	  data: function data() {
	    return {
	      show: false,
	      tipModel: {}
	    };
	  },
	  computed: {},
	  watch: {
	    tipInfo: function tipInfo(newTipInfo) {
	      var _this = this;

	      if (newTipInfo.show) {
	        this.show = true;
	        this.tipModel = newTipInfo;

	        setTimeout(function () {
	          _this.show = false;
	        }, 2000);
	      }
	    }
	  },
	  created: function created() {},
	  mounted: function mounted() {},

	  methods: {}
	};

/***/ },
/* 273 */
/***/ function(module, exports) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('transition', {
	    attrs: {
	      "name": "fade"
	    }
	  }, [(_vm.show) ? _c('div', {
	    staticClass: "des-tips-bar"
	  }, [_c('div', {
	    staticClass: "des-tips-bar-container"
	  }, [(_vm.tipModel.type === 'success') ? _c('i', {
	    staticClass: "icon-ic_correct success"
	  }) : _vm._e(), _vm._v(" "), (_vm.tipModel.type === 'fail') ? _c('i', {
	    staticClass: "icon-ic_error fail"
	  }) : _vm._e(), _vm._v(" "), (_vm.tipModel.type === 'prompt') ? _c('i', {
	    staticClass: "icon-ic_prompt prompt"
	  }) : _vm._e(), _vm._v(" "), _c('span', [_vm._v(_vm._s(_vm.tipModel.msg))])])]) : _vm._e()])
	},staticRenderFns: []}

/***/ },
/* 274 */
/***/ function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(275)

	var Component = __webpack_require__(80)(
	  /* script */
	  __webpack_require__(277),
	  /* template */
	  __webpack_require__(278),
	  /* scopeId */
	  "data-v-07a62e7b",
	  /* cssModules */
	  null
	)

	module.exports = Component.exports


/***/ },
/* 275 */
/***/ function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag

	// load the styles
	var content = __webpack_require__(276);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(78)("6da7c7cd", content, true);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-07a62e7b&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./rechargeModal.vue", function() {
	     var newContent = require("!!./../../node_modules/css-loader/index.js?minimize!./../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-07a62e7b&scoped=true!./../../node_modules/stylus-loader/index.js!./../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./rechargeModal.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ },
/* 276 */
/***/ function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(77)();
	// imports


	// module
	exports.push([module.id, ".recharge-modal[data-v-07a62e7b]{position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,.5);display:table;z-index:9999}.recharge-modal .modal-wrapper[data-v-07a62e7b]{display:table-cell;vertical-align:middle}.recharge-modal .modal-container[data-v-07a62e7b]{width:600px;position:relative;border:1px solid silver;margin:0 auto;background:#fff;text-align:center;font-size:1pc}.recharge-modal .modal-container .tilte[data-v-07a62e7b]{width:560px;height:22px;border-bottom:1px solid #d6d6d6;padding-top:14px;padding-bottom:14px;left:0;right:0;margin:auto;font-size:16px;color:#414141;text-align:left}.recharge-modal .modal-container .tilte i[data-v-07a62e7b]{color:#a6a6a6;font-size:14px;line-height:22px;float:right;cursor:pointer}.recharge-modal .modal-container .tilte i[data-v-07a62e7b]:hover{cursor:pointer;color:#6e6e6e}.recharge-modal .modal-container .payway[data-v-07a62e7b]{height:21px;padding-left:20px;padding-top:15px}.recharge-modal .modal-container .payway input[data-v-07a62e7b]{float:left;margin-top:5px;margin-right:5px}.recharge-modal .modal-container .payway img[data-v-07a62e7b]{float:left;margin-right:17px}.recharge-modal .modal-container .payway span[data-v-07a62e7b]{float:left;line-height:21px;font-size:14px;color:#828282}.recharge-modal .modal-container .serviceTerms[data-v-07a62e7b]{width:73px;height:22px;padding-left:20px;padding-top:15px;font-size:12px;color:#18ccc0;line-height:22px;text-align:left;cursor:pointer}.recharge-modal .modal-container .serviceTerms[data-v-07a62e7b]:hover{text-decoration:underline}.recharge-modal .modal-container .specialTip[data-v-07a62e7b]{width:325px;height:19px;padding-left:20px;padding-top:5px;font-size:12px;color:#828282;line-height:19px;text-align:left}.recharge-modal .modal-container .submit-wrapper[data-v-07a62e7b]{height:35px;clear:both;margin-bottom:14px}.recharge-modal .modal-container .submitBtn[data-v-07a62e7b]{width:100px;height:35px;margin-right:20px;margin-bottom:20px;float:right;background:#99c432;border-radius:3px;font-size:14px;color:#fff;line-height:35px;cursor:pointer}.recharge-modal .modal-container .submitBtn[data-v-07a62e7b]:hover{opacity:.9}.recharge-modal .modal-container ul.bind[data-v-07a62e7b]{min-height:30px;padding-top:5px}.recharge-modal .modal-container ul.bind .getVerificode[data-v-07a62e7b]{position:absolute;right:0;z-index:9999;line-height:32px;margin-right:126px;background:#18ccc0;border-radius:0 3px 3px 0;width:100px;height:30px;font-size:14px;color:#fff;text-align:center;cursor:pointer}.recharge-modal .modal-container ul.bind .warning[data-v-07a62e7b]{position:absolute;right:0;font-size:12px;color:#ff566b;z-index:9999;line-height:30px;margin-right:142px}.recharge-modal .modal-container ul[data-v-07a62e7b]{height:42px;margin:0;padding-top:19px;padding-left:20px}.recharge-modal .modal-container ul li.title[data-v-07a62e7b]{width:100%;height:30px;border:0 solid;font-size:12px;color:#939393;line-height:40px;text-align:left}.recharge-modal .modal-container ul li.bind[data-v-07a62e7b]{width:70px;list-style:none;font-size:14px;color:#828282;border:0 solid;text-align:left}.recharge-modal .modal-container ul li.attr[data-v-07a62e7b]{list-style:none;width:56px;font-size:14px;color:#828282;border:0 solid}.recharge-modal .modal-container ul li.money[data-v-07a62e7b]{cursor:pointer}.recharge-modal .modal-container ul li.active[data-v-07a62e7b]{border:1px solid #18ccc0;color:#18ccc0}.recharge-modal .modal-container ul li.select[data-v-07a62e7b]{border:0 solid;font-size:20px;color:#f79500;width:120px;text-align:left}.recharge-modal .modal-container ul li[data-v-07a62e7b]{list-style:none;width:100px;height:40px;line-height:40px;float:left;font-size:16px;color:#8c8c8c;margin-right:20px;border:1px solid #d6d6d6;border-radius:3px;text-align:center}.recharge-modal .modal-container ul input.bind[data-v-07a62e7b]{border:1px solid #d6d6d6;border-radius:3px;width:360px;height:30px;float:left;line-height:30px;font-size:12px;color:#8c8c8c;text-indent:10px;outline:none}.recharge-modal .modal-container ul input.money[data-v-07a62e7b]{width:100px;height:39px;float:left;line-height:40px;border-radius:3px;font-size:16px;border:1px solid #d6d6d6;color:#8c8c8c;text-indent:10px;outline:none}.recharge-modal .modal-container ul input.active[data-v-07a62e7b]{border:1px solid #18ccc0;color:#18ccc0}.recharge-modal .modal-container ul .money[data-v-07a62e7b]::-webkit-inner-spin-button,.recharge-modal .modal-container ul .money[data-v-07a62e7b]::-webkit-outer-spin-button{-webkit-appearance:none!important;margin:0}.warning-border[data-v-07a62e7b]{border:1px solid #f67171!important}.warning-title[data-v-07a62e7b]{color:#f67171!important}.disable[data-v-07a62e7b]{background:#afafaf!important;border:1px solid #ddd!important}", ""]);

	// exports


/***/ },
/* 277 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _postopen = __webpack_require__(263);

	var _postopen2 = _interopRequireDefault(_postopen);

	var _regExp = __webpack_require__(107);

	var _regExp2 = _interopRequireDefault(_regExp);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _tipModule = __webpack_require__(264);

	var _tipModule2 = _interopRequireDefault(_tipModule);

	var _tipsBar = __webpack_require__(269);

	var _tipsBar2 = _interopRequireDefault(_tipsBar);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	exports.default = {
	  name: 'recharge-modal',
	  props: ['show', 'businessinfo'],
	  components: {
	    'tip-module': _tipModule2.default,
	    'tips-bar': _tipsBar2.default
	  },
	  data: function data() {
	    return {
	      showRechargeModal: true,
	      showBankpayModal: false,
	      showAlipayModal: false,
	      hasMobile: window.USER_INFO ? parseInt(window.USER_INFO.check_tel) === 1 : false,
	      isThirdParty: false,
	      businessInfo: {},
	      userinfo: window.USER_INFo,
	      hasAdmin: false,
	      counting: false,
	      getcode: '获取验证码',
	      timer: null,
	      selectMoney: '100',
	      bindData: {
	        name: '',
	        tel: '',
	        verifycode: '',
	        password: ''
	      },
	      bankData: {
	        name: '',
	        tel: '',
	        company: ''
	      },
	      bankWarning: {
	        name: false,
	        tel: false,
	        company: false
	      },
	      warning: {
	        title: false,
	        name: false,
	        tel: false,
	        verifycode: false,
	        password: false
	      },
	      tipInfo: {
	        show: false
	      },
	      tipsBarInfo: {
	        show: false,
	        type: 'fail'
	      },
	      payType: 'alipay'
	    };
	  },

	  computed: {
	    texts: function texts() {
	      var self = this,
	          btnText = '保存信息';
	      if (self.hasAdmin) {
	        btnText = '确定充值';
	      }
	      return {
	        btnText: btnText
	      };
	    }
	  },
	  mounted: function mounted() {
	    var _this = this;

	    var self = this;
	    if (this.businessinfo.firstName) {
	      this.hasAdmin = true;
	    }
	    $('ul li.money').click(function () {
	      $('ul li.money').removeClass('active');
	      $('ul input.money').removeClass('active');
	      // $('ul input.money').val('自定义金额')
	      $(this).addClass('active');
	      var money = $(this).html();
	      money = money.replace('￥', '');
	      self.selectMoney = money;
	    });
	    $('ul input.money').focus(function () {
	      $('ul input.money').val('');
	      self.selectMoney = '1';
	      $('ul li.money').removeClass('active');
	      $(this).addClass('active');
	      $(this).bind('input oninput', function () {
	        self.selectMoney = $(this).val();
	      });
	    });
	    setTimeout(function () {
	      _this.hasMobile = window.USER_INFO ? parseInt(window.USER_INFO.check_tel) === 1 : false;
	    }, 2000);
	  },

	  watch: {
	    'businessinfo': function businessinfo(val, oVal) {
	      if (val.firstName) {
	        this.hasAdmin = true;
	      }
	    }
	  },
	  methods: {
	    closeRechargeModal: function closeRechargeModal() {
	      this.show.rechargeShow = false;
	    },
	    keyPress: function keyPress() {
	      var keyCode = event.keyCode;
	      if (keyCode >= 48 && keyCode <= 57) {
	        event.returnValue = true;
	      } else {
	        event.returnValue = false;
	      }
	    },
	    closeModal: function closeModal(msg) {
	      var self = this;
	    },

	    // 清除warning
	    clearWarning: function clearWarning() {
	      this.warning.name = false;
	      this.warning.tel = false;
	      this.warning.password = false;

	      this.bankWarning.name = false;
	      this.bankWarning.tel = false;
	      this.bankWarning.company = false;
	    },
	    checkAdmin: function checkAdmin() {
	      if (!_regExp2.default.realName.test(this.bindData.name)) {
	        this.warning.name = true;
	      } else {
	        this.warning.name = false;
	      }
	      return this.warning.name;
	    },
	    checkTel: function checkTel() {
	      if (this.bindData.tel == '' || !_regExp2.default.mobile.test(this.bindData.tel)) {
	        this.warning.tel = true;
	      } else {
	        this.warning.tel = false;
	      }
	      return this.warning.tel;
	    },
	    checkVerifyCode: function checkVerifyCode() {
	      if (this.bindData.verifycode == '') {
	        this.warning.verifycode = true;
	      } else {
	        this.warning.verifycode = false;
	      }
	      return this.warning.verifycode;
	    },
	    checkPassword: function checkPassword() {
	      if (!_regExp2.default.password.test(this.bindData.password)) {
	        this.warning.password = true;
	      } else {
	        this.warning.password = false;
	      }
	      return this.warning.password;
	    },
	    checkBankName: function checkBankName() {
	      if (!_regExp2.default.companyName.test(this.bankData.name)) {
	        this.bankWarning.name = true;
	      } else {
	        this.bankWarning.name = false;
	      }
	      return this.bankWarning.name;
	    },
	    checkBankTel: function checkBankTel() {
	      if (this.bankData.tel == '' || !_regExp2.default.mobile.test(this.bankData.tel)) {
	        this.bankWarning.tel = true;
	      } else {
	        this.bankWarning.tel = false;
	      }
	      return this.bankWarning.tel;
	    },
	    checkBankCompany: function checkBankCompany() {
	      if (this.bankData.company == '') {
	        this.bankWarning.company = true;
	      } else {
	        this.bankWarning.company = false;
	      }
	      return this.bankWarning.company;
	    },

	    // 重置计时器秒数 
	    resetTimer: function resetTimer(msg) {
	      clearInterval(this.timer);
	      this.getcode = msg;
	      this.counting = false;
	    },

	    // 获取过往填写的银行转账信息
	    getBankpayInfo: function getBankpayInfo() {
	      var self = this;
	      $.ajax({
	        url: '/api/user/bankpay',
	        dataType: 'json',
	        success: function success(result) {
	          var msg = result.msg;
	          if (result.code === 200) {
	            if (result.data.tel != undefined) {
	              self.bankData = result.data;
	            }
	          }
	        }
	      });
	    },
	    setBusinessInfo: function setBusinessInfo(data, callback) {
	      var self = this;
	      $.ajax({
	        url: _config2.default.API_BUSINESS_INFO,
	        type: 'POST',
	        dataType: 'json',
	        data: data,
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code === 200) {
	            self.hasAdmin = true;
	            self.texts.btnText = '确定';
	            callback();
	          } else {
	            self.tipsBarInfo = {
	              show: true,
	              type: 'fail',
	              msg: msg
	            };
	            self.texts.btnText = '保存信息';
	          }
	        },
	        error: function error() {}
	      });
	    },
	    bindPhone: function bindPhone() {
	      var self = this;
	      $.ajax({
	        url: _config2.default.API_USER_BIND_PHONE,
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          mobile: this.bindData.tel,
	          code: this.bindData.verifycode,
	          password: this.bindData.password,
	          firstName: this.bindData.name
	        },
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code === 200) {
	            self.hasAdmin = true;
	            self.texts.btnText = '确定';
	          } else {}
	        },
	        error: function error() {}
	      });
	    },

	    // 银行转账，再显示转账信息
	    setBankInfo: function setBankInfo() {
	      var self = this;
	      if (self.payType === 'bankpay') {
	        if (!self.checkBankName() && !self.checkBankTel() && !self.checkBankCompany()) {
	          self.bankData.recharge = self.selectMoney;
	        }
	      }
	    },

	    // 提交充值信息
	    submit: function submit() {
	      var self = this;
	      // 已经填写管理员信息
	      if (self.hasAdmin) {
	        if (self.payType === 'alipay') {
	          (0, _postopen2.default)(_config2.default.API_RECHARGE, { userId: window.USER_INFO.uid, recharge: parseInt(self.selectMoney), paytype: self.payType });
	          this.tipInfo = {
	            show: true,
	            tipModel: {
	              title: '支付成功了吗？',
	              content: '收款人为maka.im',
	              successName: '支付成功',
	              failName: '未成功，再来一次',
	              successCallback: function successCallback() {
	                setTimeout(function () {
	                  location.reload();
	                }, 1200);
	              }
	            }
	          };
	        } else {
	          self.setBankInfo();
	        }
	      } else {
	        // 无管理员信息，先提交管理员信息 ||  判断是否绑定手机
	        if (!self.checkAdmin()) {
	          var data = { firstName: self.bindData.name };
	          // 未绑定手机，进行绑定手机操作
	          if (!self.hasMobile) {
	            if (!self.checkTel() && !self.checkVerifyCode()) {
	              // self.bindPhone()
	              data = {
	                firstName: self.bindData.name,
	                phone: self.bindData.tel,
	                code: self.bindData.verifycode
	              };
	              if (self.isThirdParty) {
	                data.password = self.bindData.password;
	              }
	              self.setBusinessInfo(data, function () {
	                self.setBankInfo();
	              });
	            }
	          } else {
	            // 已绑定手机，进行保存管理员信息操作
	            data = { firstName: self.bindData.name, phone: self.userinfo.mobile };
	            self.setBusinessInfo(data, function () {
	              self.setBankInfo();
	            });
	          }
	        }
	      }
	    },

	    // 发送短信验证码
	    getVerifyCode: function getVerifyCode(e) {
	      e.preventDefault();
	      if (this.checkTel()) {
	        this.warning.tel = true;
	        return false;
	      }
	      this.clearWarning();
	      //正在读秒的话不能点击
	      if (this.counting === true) {
	        return;
	      }

	      this.counting = false;
	      this.getcode = '获取中...';
	      var self = this;
	      $.ajax({
	        // url: Config.API_USER_BIND_VERIFY,
	        url: _config2.default.API_ADMIN_VERIFY,
	        type: 'POST',
	        dataType: 'json',
	        data: {
	          mobile: this.bindData.tel
	        },
	        success: function success(result) {
	          var msg = result.message;
	          if (result.code == 200) {
	            //设置读秒内容，置灰
	            var second = 60;
	            self.counting = true;
	            self.getcode = second + "s后重试";

	            self.timer = setInterval(function () {
	              if (second == 0) {
	                self.resetTimer('重新获取');
	              } else {
	                second--;
	                self.getcode = second + "s后重试";
	              }
	            }, 1000);
	          } else {
	            self.tipsBarInfo = {
	              show: true,
	              type: 'fail',
	              msg: msg
	            };
	          }
	        }
	      });
	    },
	    lookSevice: function lookSevice() {
	      window.open('http://res.maka.im/common/files/MAKA%E6%9C%8D%E5%8A%A1%E6%9D%A1%E6%AC%BE20170316.pdf');
	    }
	  }
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ },
/* 278 */
/***/ function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    directives: [{
	      name: "show",
	      rawName: "v-show",
	      value: (_vm.show.rechargeShow),
	      expression: "show.rechargeShow"
	    }],
	    staticClass: "recharge-modal"
	  }, [_c('div', {
	    staticClass: "modal-wrapper"
	  }, [_c('div', {
	    staticClass: "modal-container"
	  }, [_c('div', {
	    staticClass: "tilte"
	  }, [_vm._v("充值到账户"), _c('i', {
	    staticClass: "icon-ic_close",
	    on: {
	      "click": _vm.closeRechargeModal
	    }
	  })]), _c('ul', [_c('li', {
	    staticClass: "attr"
	  }, [_vm._v("选择金额")]), _c('li', {
	    staticClass: "money active"
	  }, [_vm._v("￥100")]), _c('li', {
	    staticClass: "money"
	  }, [_vm._v("￥300")]), _c('li', {
	    staticClass: "money"
	  }, [_vm._v("￥600")]), _c('input', {
	    staticClass: "money",
	    attrs: {
	      "type": "number",
	      "min": "1",
	      "value": "自定义金额",
	      "maxlength": "6",
	      "onpaste": "return false;"
	    },
	    on: {
	      "keypress": _vm.keyPress
	    }
	  })]), _c('ul', [_c('li', {
	    staticClass: "attr"
	  }, [_vm._v("充值金额")]), _c('li', {
	    staticClass: "select"
	  }, [_vm._v("￥" + _vm._s(_vm.selectMoney) + "元")])]), _c('div', {
	    staticClass: "payway"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.payType),
	      expression: "payType"
	    }],
	    attrs: {
	      "type": "radio",
	      "value": "alipay"
	    },
	    domProps: {
	      "checked": _vm._q(_vm.payType, "alipay")
	    },
	    on: {
	      "click": function($event) {
	        _vm.payType = "alipay"
	      }
	    }
	  }), _c('img', {
	    staticClass: "zhifubao",
	    attrs: {
	      "src": __webpack_require__(279)
	    }
	  }), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.payType),
	      expression: "payType"
	    }],
	    attrs: {
	      "type": "radio",
	      "value": "bankpay"
	    },
	    domProps: {
	      "checked": _vm._q(_vm.payType, "bankpay")
	    },
	    on: {
	      "click": function($event) {
	        _vm.payType = "bankpay"
	      }
	    }
	  }), _c('span', [_vm._v("银行转账")])]), (!_vm.hasAdmin) ? _c('div', [_c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "title",
	    class: {
	      'warning-title': _vm.warning.title
	    }
	  }, [_vm._v("为了保障你的账户安全，请设置管理员"), (!_vm.hasMobile) ? _c('span', [_vm._v("和安全手机：")]) : _vm._e()])]), _c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("管理员姓名")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bindData.name),
	      expression: "bindData.name"
	    }],
	    staticClass: "bind",
	    attrs: {
	      "type": "text",
	      "placeholder": "请输入2-8位中文或英文"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bindData.name)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bindData.name = $event.target.value
	      }
	    }
	  }), (_vm.warning.name) ? _c('div', {
	    staticClass: "warning"
	  }, [_vm._v("请输入2-8位中文或英文")]) : _vm._e()]), (!_vm.hasMobile) ? _c('div', [_c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("手机号码")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bindData.tel),
	      expression: "bindData.tel"
	    }],
	    staticClass: "bind",
	    attrs: {
	      "type": "text",
	      "placeholder": "请输入11位手机号码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bindData.tel)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bindData.tel = $event.target.value
	      }
	    }
	  }), (_vm.warning.tel) ? _c('div', {
	    staticClass: "warning"
	  }, [_vm._v("请输入11位手机号码")]) : _vm._e()]), _c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("验证码")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bindData.verifycode),
	      expression: "bindData.verifycode"
	    }],
	    staticClass: "bind",
	    class: {
	      'warning-border': _vm.warning.verifycode
	    },
	    attrs: {
	      "type": "text",
	      "placeholder": "手机验证码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bindData.verifycode)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bindData.verifycode = $event.target.value
	      }
	    }
	  }), _c('div', {
	    staticClass: "getVerificode",
	    class: {
	      'disable': _vm.counting
	    },
	    on: {
	      "click": _vm.getVerifyCode
	    }
	  }, [_vm._v(_vm._s(_vm.getcode))])]), (_vm.isThirdParty) ? _c('ul', {
	    staticClass: "bind bottom"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("请设置密码")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bindData.password),
	      expression: "bindData.password"
	    }],
	    staticClass: "bind",
	    attrs: {
	      "type": "text",
	      "placeholder": "请输入6到20位由数字,字母,特殊字符组成的密码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bindData.password)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bindData.password = $event.target.value
	      }
	    }
	  }), (_vm.warning.password) ? _c('div', {
	    staticClass: "warning"
	  }, [_vm._v("请设置密码")]) : _vm._e()]) : _vm._e()]) : _vm._e()]) : _vm._e(), (_vm.payType == "bankpay") ? _c('div', [_vm._m(0), _c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("付款人")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bankData.name),
	      expression: "bankData.name"
	    }],
	    staticClass: "bind",
	    attrs: {
	      "type": "text",
	      "placeholder": "请输入2-20位中文或英文"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bankData.name)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bankData.name = $event.target.value
	      }
	    }
	  }), (_vm.bankWarning.name) ? _c('div', {
	    staticClass: "warning"
	  }, [_vm._v("请输入2-20位中文或英文")]) : _vm._e()]), _c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("手机号码")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bankData.tel),
	      expression: "bankData.tel"
	    }],
	    staticClass: "bind",
	    attrs: {
	      "type": "text",
	      "placeholder": "请输入11位手机号码"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bankData.tel)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bankData.tel = $event.target.value
	      }
	    }
	  }), (_vm.bankWarning.tel) ? _c('div', {
	    staticClass: "warning"
	  }, [_vm._v("请输入11位手机号码")]) : _vm._e()]), _c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "bind"
	  }, [_vm._v("公司名称")]), _c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.bankData.company),
	      expression: "bankData.company"
	    }],
	    staticClass: "bind",
	    attrs: {
	      "type": "text",
	      "placeholder": "请输入请输入公司名称"
	    },
	    domProps: {
	      "value": _vm._s(_vm.bankData.company)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.bankData.company = $event.target.value
	      }
	    }
	  }), (_vm.bankWarning.company) ? _c('div', {
	    staticClass: "warning"
	  }, [_vm._v("请输入请输入公司名称       ")]) : _vm._e()])]) : _vm._e(), _c('div', {
	    staticClass: "serviceTerms",
	    on: {
	      "click": _vm.lookSevice
	    }
	  }, [_vm._v("查看服务条款")]), _c('div', {
	    staticClass: "specialTip"
	  }, [_vm._v("特别说明：充值余额将无法退款，请确保充值信息无误。")]), _c('div', {
	    staticClass: "submit-wrapper"
	  }, [_c('div', {
	    staticClass: "submitBtn",
	    on: {
	      "click": _vm.submit
	    }
	  }, [_vm._v(_vm._s(_vm.texts.btnText) + "   ")])])])]), _c('tip-module', {
	    attrs: {
	      "tip-info": _vm.tipInfo
	    }
	  }), _c('tips-bar', {
	    attrs: {
	      "tip-info": _vm.tipsBarInfo
	    }
	  })], 1)
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('ul', {
	    staticClass: "bind"
	  }, [_c('li', {
	    staticClass: "title"
	  }, [_vm._v("为方便确认您的付款状态，请填写付款人相关信息：")])])
	}]}

/***/ },
/* 279 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "3a9a711c2313cdf0db3199c29609a0bc.png";

/***/ },
/* 280 */
/***/ function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "des-template-pay"
	  }, [(_vm.price === 0 && !_vm.hasTemplate) ? _c('div', [_c('div', {
	    staticClass: "des-buy-price"
	  }, [_vm._v("价格 : 免费")]), _vm._v(" "), _c('div', {
	    staticClass: "des-buy-button des-button",
	    on: {
	      "click": _vm.useTemplate
	    }
	  }, [_vm._v("立即使用")])]) : _vm._e(), _vm._v(" "), (_vm.price && !_vm.hasTemplate) ? _c('div', {
	    class: {
	      'des-paying': _vm.isBuy
	    }
	  }, [_c('div', {
	    staticClass: "des-buy-price"
	  }, [_vm._v("价格 : ￥" + _vm._s(_vm.price))]), _vm._v(" "), _c('div', {
	    staticClass: "des-buy-button2 des-button",
	    on: {
	      "click": _vm.buyTemplate
	    }
	  }, [_vm._v("立刻购买")]), _vm._v(" "), _c('div', {
	    staticClass: "des-pay"
	  }, [_c('form', {
	    staticClass: "des-form"
	  }, [_c('div', {
	    staticClass: "des-radio"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.payway),
	      expression: "payway"
	    }],
	    attrs: {
	      "type": "radio",
	      "id": "payway1",
	      "value": "balance",
	      "disabled": _vm.balance < _vm.price
	    },
	    domProps: {
	      "checked": _vm._q(_vm.payway, "balance")
	    },
	    on: {
	      "click": function($event) {
	        _vm.payway = "balance"
	      }
	    }
	  }), _vm._v(" "), _c('div', [_c('label', {
	    staticClass: "des-radio-label",
	    class: {
	      'un-choics': _vm.balance < _vm.price
	    },
	    attrs: {
	      "for": "payway1"
	    }
	  }, [_vm._v("余额支付 "), _c('span', {
	    staticClass: "des-radio-nowarp"
	  }, [_vm._v("( ￥" + _vm._s(_vm.balance) + " )")])]), _vm._v(" "), (_vm.balance < _vm.price) ? _c('div', {
	    staticClass: "des-less-tips"
	  }, [_vm._v("余额不足，\n              "), _c('a', {
	    staticClass: "des-to-rechange",
	    on: {
	      "click": _vm.showRecharg
	    }
	  }, [_vm._v("去充值")])]) : _vm._e()])]), _vm._v(" "), _c('div', {
	    staticClass: "des-radio"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.payway),
	      expression: "payway"
	    }],
	    attrs: {
	      "type": "radio",
	      "id": "payway2",
	      "value": "alipay"
	    },
	    domProps: {
	      "checked": _vm._q(_vm.payway, "alipay")
	    },
	    on: {
	      "click": function($event) {
	        _vm.payway = "alipay"
	      }
	    }
	  }), _vm._v(" "), _vm._m(0)]), _vm._v(" "), _c('div', {
	    staticClass: "des-radio"
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.payway),
	      expression: "payway"
	    }],
	    attrs: {
	      "type": "radio",
	      "id": "payway3",
	      "value": "template_coupon",
	      "disabled": !_vm.couponInfo.template
	    },
	    domProps: {
	      "checked": _vm._q(_vm.payway, "template_coupon")
	    },
	    on: {
	      "click": function($event) {
	        _vm.payway = "template_coupon"
	      }
	    }
	  }), _vm._v(" "), _c('label', {
	    staticClass: "des-radio-label",
	    class: {
	      'un-choics': !_vm.couponInfo.template
	    },
	    attrs: {
	      "for": "payway3"
	    }
	  }, [_vm._v("兑换券\n            "), (_vm.couponInfo.template) ? _c('span', {
	    staticClass: "des-radio-notes"
	  }, [_vm._v("使用" + _vm._s(_vm.couponInfo.template) + "张券兑换")]) : _c('span', {
	    staticClass: "des-radio-notes"
	  }, [_vm._v("模板劵数量不足")])])]), _vm._v(" "), _c('div', {
	    staticClass: "des-radio"
	  }, [_vm._v("\n          应付: "), _c('span', {
	    staticClass: "des-pay-price"
	  }, [_vm._v("￥" + _vm._s(_vm.price))])])]), _vm._v(" "), _c('div', {
	    staticClass: "des-pay-button des-button",
	    on: {
	      "click": _vm.payMoney
	    }
	  }, [_vm._v("确认支付")])])]) : _vm._e(), _vm._v(" "), (_vm.hasTemplate) ? _c('div', [_c('div', {
	    staticClass: "des-pay-button des-button",
	    on: {
	      "click": _vm.useTemplate
	    }
	  }, [_vm._v("马上使用")])]) : _vm._e(), _vm._v(" "), _c('tip-module', {
	    attrs: {
	      "tip-info": _vm.tipInfo
	    }
	  }), _vm._v(" "), _c('tips-bar', {
	    attrs: {
	      "tip-info": _vm.tipsBarInfo
	    }
	  }), _vm._v(" "), _c('recharge-modal', {
	    attrs: {
	      "show": _vm.rechargeShow,
	      "businessinfo": _vm.businessInfo
	    }
	  })], 1)
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('label', {
	    staticClass: "des-radio-label",
	    attrs: {
	      "for": "payway2"
	    }
	  }, [_c('img', {
	    attrs: {
	      "src": __webpack_require__(279)
	    }
	  })])
	}]}

/***/ },
/* 281 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	__webpack_require__(282);

	__webpack_require__(235);

	__webpack_require__(236);

	__webpack_require__(238);

	var _templatePreviewCompent = __webpack_require__(283);

	window.onload = (0, _templatePreviewCompent.initPreview)(); // import utils from '../../utils/utils.js'
	// import config from '../../config.js'

	var bannerDom = $('.des-template-preview-banner')[0];
	bannerDom.src = __webpack_require__(287);

/***/ },
/* 282 */
/***/ function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ },
/* 283 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	Object.defineProperty(exports, "__esModule", {
	  value: true
	});

	var _stringify = __webpack_require__(195);

	var _stringify2 = _interopRequireDefault(_stringify);

	var _typeof2 = __webpack_require__(88);

	var _typeof3 = _interopRequireDefault(_typeof2);

	exports.initPreview = initPreview;
	exports.unMountPreview = unMountPreview;

	var _utils = __webpack_require__(87);

	var _utils2 = _interopRequireDefault(_utils);

	var _config = __webpack_require__(82);

	var _config2 = _interopRequireDefault(_config);

	var _index = __webpack_require__(284);

	var _index2 = _interopRequireDefault(_index);

	__webpack_require__(286);

	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

	// import iscroll from 'iscroll'

	var viewerUrl = void 0;
	var qrcodeImg = {};
	var viewerIframe = {};
	var currentSumDom = void 0;
	var totalNum = void 0;
	var currentNum = void 0;

	function initPreview() {
	  viewerUrl = _config2.default.API_VIEWER_URL + window.TEMPLATE_INFO.id + '?mode=storeTemplate&TempAdmode=true';
	  qrcodeImg = document.getElementsByClassName('des-qrcode-qr')[0];
	  currentSumDom = document.getElementsByClassName('des-page-num')[0];
	  viewerIframe = document.getElementById('viewer-iframe');
	  totalNum = window.TEMPLATE_INFO.pageLength;
	  currentNum = 1;

	  var templateScroll = new IScroll('#templateScroll', {
	    mouseWheel: true,
	    scrollbars: true
	  });

	  var qrcode = _index2.default.toDataURL(viewerUrl, 4);

	  qrcodeImg.src = qrcode;

	  viewerIframe.src = viewerUrl;

	  // 初始化页数
	  currentSumDom.innerHTML = currentNum + ' / ' + totalNum;

	  window.postMsg = postMsg;

	  function postMsg(str) {
	    var msg = str.dataset.msg;
	    var obj = void 0;

	    if (msg === 'prev') {

	      if (currentNum === 1) return;
	      obj = { type: 'prevPage' };
	      currentNum--;
	    } else if (msg === 'next') {

	      if (currentNum === totalNum) return;
	      obj = { type: 'nextPage' };
	      currentNum++;
	    } else {
	      currentNum = parseInt(msg) + 1;
	      obj = { type: 'showPage', index: currentNum - 1 };
	      _utils2.default.changeClass(str, 'curent-page');
	    }

	    // 重置页数
	    currentSumDom.innerHTML = currentNum + ' / ' + totalNum;

	    if ((typeof obj === 'undefined' ? 'undefined' : (0, _typeof3.default)(obj)) !== 'object') {
	      return;
	    }

	    var objStr = (0, _stringify2.default)(obj);
	    viewerIframe.contentWindow.postMessage(objStr, '*');
	    window.postMessage(objStr, '*');
	  }
	}

	function unMountPreview() {
	  viewerUrl = null;
	  // 如果 initPreview 执行成功才设置
	  if (qrcodeImg.src) {
	    qrcodeImg.src = null;
	    viewerIframe.src = null;
	  }
	  totalNum = null;
	  window.postMsg = null;
	}

/***/ },
/* 284 */
/***/ function(module, exports, __webpack_require__) {

	'use strict';

	var qrcode = __webpack_require__(285);

	module.exports = {
	    typeNumber: 4,
	    errorCorrectLevel: 'L',
	    toBase64: function toBase64(text, size) {
	        var qr = qrcode(this.typeNumber, this.errorCorrectLevel);
	        qr.addData(text);
	        qr.make();
	        var base64 = qr.createImgBase64(size);
	        return base64;
	    },
	    toDataURL: function toDataURL(text, size) {
	        var base64 = this.toBase64(text, size);
	        var dataURL = 'data:image/gif;base64,' + base64;
	        return dataURL;
	    }
	};

/***/ },
/* 285 */
/***/ function(module, exports) {

	'use strict';

	// The original source of this file is: http://d-project.googlecode.com/svn/trunk/misc/qrcode/js/qrcode.js

	//---------------------------------------------------------------------
	//
	// QR Code Generator for JavaScript
	//
	// Copyright (c) 2009 Kazuhiko Arase
	//
	// URL: http://www.d-project.com/
	//
	// Licensed under the MIT license:
	//	http://www.opensource.org/licenses/mit-license.php
	//
	// The word 'QR Code' is registered trademark of
	// DENSO WAVE INCORPORATED
	//	http://www.denso-wave.com/qrcode/faqpatent-e.html
	//
	//---------------------------------------------------------------------

	var qrcode = function () {

		//---------------------------------------------------------------------
		// qrcode
		//---------------------------------------------------------------------

		/**
	  * qrcode
	  * @param typeNumber 1 to 10
	  * @param errorCorrectLevel 'L','M','Q','H'
	  */
		var qrcode = function qrcode(typeNumber, errorCorrectLevel) {

			var PAD0 = 0xEC;
			var PAD1 = 0x11;

			var _typeNumber = typeNumber;
			var _errorCorrectLevel = QRErrorCorrectLevel[errorCorrectLevel];
			var _modules = null;
			var _moduleCount = 0;
			var _dataCache = null;
			var _dataList = new Array();

			var _this = {};

			var makeImpl = function makeImpl(test, maskPattern) {

				_moduleCount = _typeNumber * 4 + 17;
				_modules = function (moduleCount) {
					var modules = new Array(moduleCount);
					for (var row = 0; row < moduleCount; row += 1) {
						modules[row] = new Array(moduleCount);
						for (var col = 0; col < moduleCount; col += 1) {
							modules[row][col] = null;
						}
					}
					return modules;
				}(_moduleCount);

				setupPositionProbePattern(0, 0);
				setupPositionProbePattern(_moduleCount - 7, 0);
				setupPositionProbePattern(0, _moduleCount - 7);
				setupPositionAdjustPattern();
				setupTimingPattern();
				setupTypeInfo(test, maskPattern);

				if (_typeNumber >= 7) {
					setupTypeNumber(test);
				}

				if (_dataCache == null) {
					_dataCache = createData(_typeNumber, _errorCorrectLevel, _dataList);
				}

				mapData(_dataCache, maskPattern);
			};

			var setupPositionProbePattern = function setupPositionProbePattern(row, col) {

				for (var r = -1; r <= 7; r += 1) {

					if (row + r <= -1 || _moduleCount <= row + r) continue;

					for (var c = -1; c <= 7; c += 1) {

						if (col + c <= -1 || _moduleCount <= col + c) continue;

						if (0 <= r && r <= 6 && (c == 0 || c == 6) || 0 <= c && c <= 6 && (r == 0 || r == 6) || 2 <= r && r <= 4 && 2 <= c && c <= 4) {
							_modules[row + r][col + c] = true;
						} else {
							_modules[row + r][col + c] = false;
						}
					}
				}
			};

			var getBestMaskPattern = function getBestMaskPattern() {

				var minLostPoint = 0;
				var pattern = 0;

				for (var i = 0; i < 8; i += 1) {

					makeImpl(true, i);

					var lostPoint = QRUtil.getLostPoint(_this);

					if (i == 0 || minLostPoint > lostPoint) {
						minLostPoint = lostPoint;
						pattern = i;
					}
				}

				return pattern;
			};

			var setupTimingPattern = function setupTimingPattern() {

				for (var r = 8; r < _moduleCount - 8; r += 1) {
					if (_modules[r][6] != null) {
						continue;
					}
					_modules[r][6] = r % 2 == 0;
				}

				for (var c = 8; c < _moduleCount - 8; c += 1) {
					if (_modules[6][c] != null) {
						continue;
					}
					_modules[6][c] = c % 2 == 0;
				}
			};

			var setupPositionAdjustPattern = function setupPositionAdjustPattern() {

				var pos = QRUtil.getPatternPosition(_typeNumber);

				for (var i = 0; i < pos.length; i += 1) {

					for (var j = 0; j < pos.length; j += 1) {

						var row = pos[i];
						var col = pos[j];

						if (_modules[row][col] != null) {
							continue;
						}

						for (var r = -2; r <= 2; r += 1) {

							for (var c = -2; c <= 2; c += 1) {

								if (r == -2 || r == 2 || c == -2 || c == 2 || r == 0 && c == 0) {
									_modules[row + r][col + c] = true;
								} else {
									_modules[row + r][col + c] = false;
								}
							}
						}
					}
				}
			};

			var setupTypeNumber = function setupTypeNumber(test) {

				var bits = QRUtil.getBCHTypeNumber(_typeNumber);

				for (var i = 0; i < 18; i += 1) {
					var mod = !test && (bits >> i & 1) == 1;
					_modules[Math.floor(i / 3)][i % 3 + _moduleCount - 8 - 3] = mod;
				}

				for (var i = 0; i < 18; i += 1) {
					var mod = !test && (bits >> i & 1) == 1;
					_modules[i % 3 + _moduleCount - 8 - 3][Math.floor(i / 3)] = mod;
				}
			};

			var setupTypeInfo = function setupTypeInfo(test, maskPattern) {

				var data = _errorCorrectLevel << 3 | maskPattern;
				var bits = QRUtil.getBCHTypeInfo(data);

				// vertical
				for (var i = 0; i < 15; i += 1) {

					var mod = !test && (bits >> i & 1) == 1;

					if (i < 6) {
						_modules[i][8] = mod;
					} else if (i < 8) {
						_modules[i + 1][8] = mod;
					} else {
						_modules[_moduleCount - 15 + i][8] = mod;
					}
				}

				// horizontal
				for (var i = 0; i < 15; i += 1) {

					var mod = !test && (bits >> i & 1) == 1;

					if (i < 8) {
						_modules[8][_moduleCount - i - 1] = mod;
					} else if (i < 9) {
						_modules[8][15 - i - 1 + 1] = mod;
					} else {
						_modules[8][15 - i - 1] = mod;
					}
				}

				// fixed module
				_modules[_moduleCount - 8][8] = !test;
			};

			var mapData = function mapData(data, maskPattern) {

				var inc = -1;
				var row = _moduleCount - 1;
				var bitIndex = 7;
				var byteIndex = 0;
				var maskFunc = QRUtil.getMaskFunction(maskPattern);

				for (var col = _moduleCount - 1; col > 0; col -= 2) {

					if (col == 6) col -= 1;

					while (true) {

						for (var c = 0; c < 2; c += 1) {

							if (_modules[row][col - c] == null) {

								var dark = false;

								if (byteIndex < data.length) {
									dark = (data[byteIndex] >>> bitIndex & 1) == 1;
								}

								var mask = maskFunc(row, col - c);

								if (mask) {
									dark = !dark;
								}

								_modules[row][col - c] = dark;
								bitIndex -= 1;

								if (bitIndex == -1) {
									byteIndex += 1;
									bitIndex = 7;
								}
							}
						}

						row += inc;

						if (row < 0 || _moduleCount <= row) {
							row -= inc;
							inc = -inc;
							break;
						}
					}
				}
			};

			var createBytes = function createBytes(buffer, rsBlocks) {

				var offset = 0;

				var maxDcCount = 0;
				var maxEcCount = 0;

				var dcdata = new Array(rsBlocks.length);
				var ecdata = new Array(rsBlocks.length);

				for (var r = 0; r < rsBlocks.length; r += 1) {

					var dcCount = rsBlocks[r].dataCount;
					var ecCount = rsBlocks[r].totalCount - dcCount;

					maxDcCount = Math.max(maxDcCount, dcCount);
					maxEcCount = Math.max(maxEcCount, ecCount);

					dcdata[r] = new Array(dcCount);

					for (var i = 0; i < dcdata[r].length; i += 1) {
						dcdata[r][i] = 0xff & buffer.getBuffer()[i + offset];
					}
					offset += dcCount;

					var rsPoly = QRUtil.getErrorCorrectPolynomial(ecCount);
					var rawPoly = qrPolynomial(dcdata[r], rsPoly.getLength() - 1);

					var modPoly = rawPoly.mod(rsPoly);
					ecdata[r] = new Array(rsPoly.getLength() - 1);
					for (var i = 0; i < ecdata[r].length; i += 1) {
						var modIndex = i + modPoly.getLength() - ecdata[r].length;
						ecdata[r][i] = modIndex >= 0 ? modPoly.getAt(modIndex) : 0;
					}
				}

				var totalCodeCount = 0;
				for (var i = 0; i < rsBlocks.length; i += 1) {
					totalCodeCount += rsBlocks[i].totalCount;
				}

				var data = new Array(totalCodeCount);
				var index = 0;

				for (var i = 0; i < maxDcCount; i += 1) {
					for (var r = 0; r < rsBlocks.length; r += 1) {
						if (i < dcdata[r].length) {
							data[index] = dcdata[r][i];
							index += 1;
						}
					}
				}

				for (var i = 0; i < maxEcCount; i += 1) {
					for (var r = 0; r < rsBlocks.length; r += 1) {
						if (i < ecdata[r].length) {
							data[index] = ecdata[r][i];
							index += 1;
						}
					}
				}

				return data;
			};

			var createData = function createData(typeNumber, errorCorrectLevel, dataList) {

				var rsBlocks = QRRSBlock.getRSBlocks(typeNumber, errorCorrectLevel);

				var buffer = qrBitBuffer();

				for (var i = 0; i < dataList.length; i += 1) {
					var data = dataList[i];
					buffer.put(data.getMode(), 4);
					buffer.put(data.getLength(), QRUtil.getLengthInBits(data.getMode(), typeNumber));
					data.write(buffer);
				}

				// calc num max data.
				var totalDataCount = 0;
				for (var i = 0; i < rsBlocks.length; i += 1) {
					totalDataCount += rsBlocks[i].dataCount;
				}

				if (buffer.getLengthInBits() > totalDataCount * 8) {
					throw new Error('code length overflow. (' + buffer.getLengthInBits() + '>' + totalDataCount * 8 + ')');
				}

				// end code
				if (buffer.getLengthInBits() + 4 <= totalDataCount * 8) {
					buffer.put(0, 4);
				}

				// padding
				while (buffer.getLengthInBits() % 8 != 0) {
					buffer.putBit(false);
				}

				// padding
				while (true) {

					if (buffer.getLengthInBits() >= totalDataCount * 8) {
						break;
					}
					buffer.put(PAD0, 8);

					if (buffer.getLengthInBits() >= totalDataCount * 8) {
						break;
					}
					buffer.put(PAD1, 8);
				}

				return createBytes(buffer, rsBlocks);
			};

			_this.addData = function (data) {
				var newData = qr8BitByte(data);
				_dataList.push(newData);
				_dataCache = null;
			};

			_this.isDark = function (row, col) {
				if (row < 0 || _moduleCount <= row || col < 0 || _moduleCount <= col) {
					throw new Error(row + ',' + col);
				}
				return _modules[row][col];
			};

			_this.getModuleCount = function () {
				return _moduleCount;
			};

			_this.make = function () {
				makeImpl(false, getBestMaskPattern());
			};

			_this.createTableTag = function (cellSize, margin) {

				cellSize = cellSize || 2;
				margin = typeof margin == 'undefined' ? cellSize * 4 : margin;

				var qrHtml = '';

				qrHtml += '<table style="';
				qrHtml += ' border-width: 0px; border-style: none;';
				qrHtml += ' border-collapse: collapse;';
				qrHtml += ' padding: 0px; margin: ' + margin + 'px;';
				qrHtml += '">';
				qrHtml += '<tbody>';

				for (var r = 0; r < _this.getModuleCount(); r += 1) {

					qrHtml += '<tr>';

					for (var c = 0; c < _this.getModuleCount(); c += 1) {
						qrHtml += '<td style="';
						qrHtml += ' border-width: 0px; border-style: none;';
						qrHtml += ' border-collapse: collapse;';
						qrHtml += ' padding: 0px; margin: 0px;';
						qrHtml += ' width: ' + cellSize + 'px;';
						qrHtml += ' height: ' + cellSize + 'px;';
						qrHtml += ' background-color: ';
						qrHtml += _this.isDark(r, c) ? '#000000' : '#ffffff';
						qrHtml += ';';
						qrHtml += '"/>';
					}

					qrHtml += '</tr>';
				}

				qrHtml += '</tbody>';
				qrHtml += '</table>';

				return qrHtml;
			};

			_this.createImgTag = function (cellSize, margin) {

				cellSize = cellSize || 2;
				margin = typeof margin == 'undefined' ? cellSize * 4 : margin;

				var size = _this.getModuleCount() * cellSize + margin * 2;
				var min = margin;
				var max = size - margin;

				return createImgTag(size, size, function (x, y) {
					if (min <= x && x < max && min <= y && y < max) {
						var c = Math.floor((x - min) / cellSize);
						var r = Math.floor((y - min) / cellSize);
						return _this.isDark(r, c) ? 0 : 1;
					} else {
						return 1;
					}
				});
			};

			_this.createImgBase64 = function (cellSize, margin) {

				cellSize = cellSize || 2;
				margin = typeof margin == 'undefined' ? cellSize * 4 : margin;

				var size = _this.getModuleCount() * cellSize + margin * 2;
				var min = margin;
				var max = size - margin;

				return createImgBase64(size, size, function (x, y) {
					if (min <= x && x < max && min <= y && y < max) {
						var c = Math.floor((x - min) / cellSize);
						var r = Math.floor((y - min) / cellSize);
						return _this.isDark(r, c) ? 0 : 1;
					} else {
						return 1;
					}
				});
			};

			return _this;
		};

		//---------------------------------------------------------------------
		// qrcode.stringToBytes
		//---------------------------------------------------------------------

		qrcode.stringToBytes = function (s) {
			var bytes = new Array();
			for (var i = 0; i < s.length; i += 1) {
				var c = s.charCodeAt(i);
				bytes.push(c & 0xff);
			}
			return bytes;
		};

		//---------------------------------------------------------------------
		// qrcode.createStringToBytes
		//---------------------------------------------------------------------

		/**
	  * @param unicodeData base64 string of byte array.
	  * [16bit Unicode],[16bit Bytes], ...
	  * @param numChars
	  */
		qrcode.createStringToBytes = function (unicodeData, numChars) {

			// create conversion map.

			var unicodeMap = function () {

				var bin = base64DecodeInputStream(unicodeData);
				var read = function read() {
					var b = bin.read();
					if (b == -1) throw new Error();
					return b;
				};

				var count = 0;
				var unicodeMap = {};
				while (true) {
					var b0 = bin.read();
					if (b0 == -1) break;
					var b1 = read();
					var b2 = read();
					var b3 = read();
					var k = String.fromCharCode(b0 << 8 | b1);
					var v = b2 << 8 | b3;
					unicodeMap[k] = v;
					count += 1;
				}
				if (count != numChars) {
					throw new Error(count + ' != ' + numChars);
				}

				return unicodeMap;
			}();

			var unknownChar = '?'.charCodeAt(0);

			return function (s) {
				var bytes = new Array();
				for (var i = 0; i < s.length; i += 1) {
					var c = s.charCodeAt(i);
					if (c < 128) {
						bytes.push(c);
					} else {
						var b = unicodeMap[s.charAt(i)];
						if (typeof b == 'number') {
							if ((b & 0xff) == b) {
								// 1byte
								bytes.push(b);
							} else {
								// 2bytes
								bytes.push(b >>> 8);
								bytes.push(b & 0xff);
							}
						} else {
							bytes.push(unknownChar);
						}
					}
				}
				return bytes;
			};
		};

		//---------------------------------------------------------------------
		// QRMode
		//---------------------------------------------------------------------

		var QRMode = {
			MODE_NUMBER: 1 << 0,
			MODE_ALPHA_NUM: 1 << 1,
			MODE_8BIT_BYTE: 1 << 2,
			MODE_KANJI: 1 << 3
		};

		//---------------------------------------------------------------------
		// QRErrorCorrectLevel
		//---------------------------------------------------------------------

		var QRErrorCorrectLevel = {
			L: 1,
			M: 0,
			Q: 3,
			H: 2
		};

		//---------------------------------------------------------------------
		// QRMaskPattern
		//---------------------------------------------------------------------

		var QRMaskPattern = {
			PATTERN000: 0,
			PATTERN001: 1,
			PATTERN010: 2,
			PATTERN011: 3,
			PATTERN100: 4,
			PATTERN101: 5,
			PATTERN110: 6,
			PATTERN111: 7
		};

		//---------------------------------------------------------------------
		// QRUtil
		//---------------------------------------------------------------------

		var QRUtil = function () {

			var PATTERN_POSITION_TABLE = [[], [6, 18], [6, 22], [6, 26], [6, 30], [6, 34], [6, 22, 38], [6, 24, 42], [6, 26, 46], [6, 28, 50], [6, 30, 54], [6, 32, 58], [6, 34, 62], [6, 26, 46, 66], [6, 26, 48, 70], [6, 26, 50, 74], [6, 30, 54, 78], [6, 30, 56, 82], [6, 30, 58, 86], [6, 34, 62, 90], [6, 28, 50, 72, 94], [6, 26, 50, 74, 98], [6, 30, 54, 78, 102], [6, 28, 54, 80, 106], [6, 32, 58, 84, 110], [6, 30, 58, 86, 114], [6, 34, 62, 90, 118], [6, 26, 50, 74, 98, 122], [6, 30, 54, 78, 102, 126], [6, 26, 52, 78, 104, 130], [6, 30, 56, 82, 108, 134], [6, 34, 60, 86, 112, 138], [6, 30, 58, 86, 114, 142], [6, 34, 62, 90, 118, 146], [6, 30, 54, 78, 102, 126, 150], [6, 24, 50, 76, 102, 128, 154], [6, 28, 54, 80, 106, 132, 158], [6, 32, 58, 84, 110, 136, 162], [6, 26, 54, 82, 110, 138, 166], [6, 30, 58, 86, 114, 142, 170]];
			var G15 = 1 << 10 | 1 << 8 | 1 << 5 | 1 << 4 | 1 << 2 | 1 << 1 | 1 << 0;
			var G18 = 1 << 12 | 1 << 11 | 1 << 10 | 1 << 9 | 1 << 8 | 1 << 5 | 1 << 2 | 1 << 0;
			var G15_MASK = 1 << 14 | 1 << 12 | 1 << 10 | 1 << 4 | 1 << 1;

			var _this = {};

			var getBCHDigit = function getBCHDigit(data) {
				var digit = 0;
				while (data != 0) {
					digit += 1;
					data >>>= 1;
				}
				return digit;
			};

			_this.getBCHTypeInfo = function (data) {
				var d = data << 10;
				while (getBCHDigit(d) - getBCHDigit(G15) >= 0) {
					d ^= G15 << getBCHDigit(d) - getBCHDigit(G15);
				}
				return (data << 10 | d) ^ G15_MASK;
			};

			_this.getBCHTypeNumber = function (data) {
				var d = data << 12;
				while (getBCHDigit(d) - getBCHDigit(G18) >= 0) {
					d ^= G18 << getBCHDigit(d) - getBCHDigit(G18);
				}
				return data << 12 | d;
			};

			_this.getPatternPosition = function (typeNumber) {
				return PATTERN_POSITION_TABLE[typeNumber - 1];
			};

			_this.getMaskFunction = function (maskPattern) {

				switch (maskPattern) {

					case QRMaskPattern.PATTERN000:
						return function (i, j) {
							return (i + j) % 2 == 0;
						};
					case QRMaskPattern.PATTERN001:
						return function (i, j) {
							return i % 2 == 0;
						};
					case QRMaskPattern.PATTERN010:
						return function (i, j) {
							return j % 3 == 0;
						};
					case QRMaskPattern.PATTERN011:
						return function (i, j) {
							return (i + j) % 3 == 0;
						};
					case QRMaskPattern.PATTERN100:
						return function (i, j) {
							return (Math.floor(i / 2) + Math.floor(j / 3)) % 2 == 0;
						};
					case QRMaskPattern.PATTERN101:
						return function (i, j) {
							return i * j % 2 + i * j % 3 == 0;
						};
					case QRMaskPattern.PATTERN110:
						return function (i, j) {
							return (i * j % 2 + i * j % 3) % 2 == 0;
						};
					case QRMaskPattern.PATTERN111:
						return function (i, j) {
							return (i * j % 3 + (i + j) % 2) % 2 == 0;
						};

					default:
						throw new Error('bad maskPattern:' + maskPattern);
				}
			};

			_this.getErrorCorrectPolynomial = function (errorCorrectLength) {
				var a = qrPolynomial([1], 0);
				for (var i = 0; i < errorCorrectLength; i += 1) {
					a = a.multiply(qrPolynomial([1, QRMath.gexp(i)], 0));
				}
				return a;
			};

			_this.getLengthInBits = function (mode, type) {

				if (1 <= type && type < 10) {

					// 1 - 9

					switch (mode) {
						case QRMode.MODE_NUMBER:
							return 10;
						case QRMode.MODE_ALPHA_NUM:
							return 9;
						case QRMode.MODE_8BIT_BYTE:
							return 8;
						case QRMode.MODE_KANJI:
							return 8;
						default:
							throw new Error('mode:' + mode);
					}
				} else if (type < 27) {

					// 10 - 26

					switch (mode) {
						case QRMode.MODE_NUMBER:
							return 12;
						case QRMode.MODE_ALPHA_NUM:
							return 11;
						case QRMode.MODE_8BIT_BYTE:
							return 16;
						case QRMode.MODE_KANJI:
							return 10;
						default:
							throw new Error('mode:' + mode);
					}
				} else if (type < 41) {

					// 27 - 40

					switch (mode) {
						case QRMode.MODE_NUMBER:
							return 14;
						case QRMode.MODE_ALPHA_NUM:
							return 13;
						case QRMode.MODE_8BIT_BYTE:
							return 16;
						case QRMode.MODE_KANJI:
							return 12;
						default:
							throw new Error('mode:' + mode);
					}
				} else {
					throw new Error('type:' + type);
				}
			};

			_this.getLostPoint = function (qrcode) {

				var moduleCount = qrcode.getModuleCount();

				var lostPoint = 0;

				// LEVEL1

				for (var row = 0; row < moduleCount; row += 1) {
					for (var col = 0; col < moduleCount; col += 1) {

						var sameCount = 0;
						var dark = qrcode.isDark(row, col);

						for (var r = -1; r <= 1; r += 1) {

							if (row + r < 0 || moduleCount <= row + r) {
								continue;
							}

							for (var c = -1; c <= 1; c += 1) {

								if (col + c < 0 || moduleCount <= col + c) {
									continue;
								}

								if (r == 0 && c == 0) {
									continue;
								}

								if (dark == qrcode.isDark(row + r, col + c)) {
									sameCount += 1;
								}
							}
						}

						if (sameCount > 5) {
							lostPoint += 3 + sameCount - 5;
						}
					}
				};

				// LEVEL2

				for (var row = 0; row < moduleCount - 1; row += 1) {
					for (var col = 0; col < moduleCount - 1; col += 1) {
						var count = 0;
						if (qrcode.isDark(row, col)) count += 1;
						if (qrcode.isDark(row + 1, col)) count += 1;
						if (qrcode.isDark(row, col + 1)) count += 1;
						if (qrcode.isDark(row + 1, col + 1)) count += 1;
						if (count == 0 || count == 4) {
							lostPoint += 3;
						}
					}
				}

				// LEVEL3

				for (var row = 0; row < moduleCount; row += 1) {
					for (var col = 0; col < moduleCount - 6; col += 1) {
						if (qrcode.isDark(row, col) && !qrcode.isDark(row, col + 1) && qrcode.isDark(row, col + 2) && qrcode.isDark(row, col + 3) && qrcode.isDark(row, col + 4) && !qrcode.isDark(row, col + 5) && qrcode.isDark(row, col + 6)) {
							lostPoint += 40;
						}
					}
				}

				for (var col = 0; col < moduleCount; col += 1) {
					for (var row = 0; row < moduleCount - 6; row += 1) {
						if (qrcode.isDark(row, col) && !qrcode.isDark(row + 1, col) && qrcode.isDark(row + 2, col) && qrcode.isDark(row + 3, col) && qrcode.isDark(row + 4, col) && !qrcode.isDark(row + 5, col) && qrcode.isDark(row + 6, col)) {
							lostPoint += 40;
						}
					}
				}

				// LEVEL4

				var darkCount = 0;

				for (var col = 0; col < moduleCount; col += 1) {
					for (var row = 0; row < moduleCount; row += 1) {
						if (qrcode.isDark(row, col)) {
							darkCount += 1;
						}
					}
				}

				var ratio = Math.abs(100 * darkCount / moduleCount / moduleCount - 50) / 5;
				lostPoint += ratio * 10;

				return lostPoint;
			};

			return _this;
		}();

		//---------------------------------------------------------------------
		// QRMath
		//---------------------------------------------------------------------

		var QRMath = function () {

			var EXP_TABLE = new Array(256);
			var LOG_TABLE = new Array(256);

			// initialize tables
			for (var i = 0; i < 8; i += 1) {
				EXP_TABLE[i] = 1 << i;
			}
			for (var i = 8; i < 256; i += 1) {
				EXP_TABLE[i] = EXP_TABLE[i - 4] ^ EXP_TABLE[i - 5] ^ EXP_TABLE[i - 6] ^ EXP_TABLE[i - 8];
			}
			for (var i = 0; i < 255; i += 1) {
				LOG_TABLE[EXP_TABLE[i]] = i;
			}

			var _this = {};

			_this.glog = function (n) {

				if (n < 1) {
					throw new Error('glog(' + n + ')');
				}

				return LOG_TABLE[n];
			};

			_this.gexp = function (n) {

				while (n < 0) {
					n += 255;
				}

				while (n >= 256) {
					n -= 255;
				}

				return EXP_TABLE[n];
			};

			return _this;
		}();

		//---------------------------------------------------------------------
		// qrPolynomial
		//---------------------------------------------------------------------

		function qrPolynomial(num, shift) {

			if (typeof num.length == 'undefined') {
				throw new Error(num.length + '/' + shift);
			}

			var _num = function () {
				var offset = 0;
				while (offset < num.length && num[offset] == 0) {
					offset += 1;
				}
				var _num = new Array(num.length - offset + shift);
				for (var i = 0; i < num.length - offset; i += 1) {
					_num[i] = num[i + offset];
				}
				return _num;
			}();

			var _this = {};

			_this.getAt = function (index) {
				return _num[index];
			};

			_this.getLength = function () {
				return _num.length;
			};

			_this.multiply = function (e) {

				var num = new Array(_this.getLength() + e.getLength() - 1);

				for (var i = 0; i < _this.getLength(); i += 1) {
					for (var j = 0; j < e.getLength(); j += 1) {
						num[i + j] ^= QRMath.gexp(QRMath.glog(_this.getAt(i)) + QRMath.glog(e.getAt(j)));
					}
				}

				return qrPolynomial(num, 0);
			};

			_this.mod = function (e) {

				if (_this.getLength() - e.getLength() < 0) {
					return _this;
				}

				var ratio = QRMath.glog(_this.getAt(0)) - QRMath.glog(e.getAt(0));

				var num = new Array(_this.getLength());
				for (var i = 0; i < _this.getLength(); i += 1) {
					num[i] = _this.getAt(i);
				}

				for (var i = 0; i < e.getLength(); i += 1) {
					num[i] ^= QRMath.gexp(QRMath.glog(e.getAt(i)) + ratio);
				}

				// recursive call
				return qrPolynomial(num, 0).mod(e);
			};

			return _this;
		};

		//---------------------------------------------------------------------
		// QRRSBlock
		//---------------------------------------------------------------------

		var QRRSBlock = function () {

			var RS_BLOCK_TABLE = [

			// L
			// M
			// Q
			// H

			// 1
			[1, 26, 19], [1, 26, 16], [1, 26, 13], [1, 26, 9],

			// 2
			[1, 44, 34], [1, 44, 28], [1, 44, 22], [1, 44, 16],

			// 3
			[1, 70, 55], [1, 70, 44], [2, 35, 17], [2, 35, 13],

			// 4
			[1, 100, 80], [2, 50, 32], [2, 50, 24], [4, 25, 9],

			// 5
			[1, 134, 108], [2, 67, 43], [2, 33, 15, 2, 34, 16], [2, 33, 11, 2, 34, 12],

			// 6
			[2, 86, 68], [4, 43, 27], [4, 43, 19], [4, 43, 15],

			// 7
			[2, 98, 78], [4, 49, 31], [2, 32, 14, 4, 33, 15], [4, 39, 13, 1, 40, 14],

			// 8
			[2, 121, 97], [2, 60, 38, 2, 61, 39], [4, 40, 18, 2, 41, 19], [4, 40, 14, 2, 41, 15],

			// 9
			[2, 146, 116], [3, 58, 36, 2, 59, 37], [4, 36, 16, 4, 37, 17], [4, 36, 12, 4, 37, 13],

			// 10
			[2, 86, 68, 2, 87, 69], [4, 69, 43, 1, 70, 44], [6, 43, 19, 2, 44, 20], [6, 43, 15, 2, 44, 16]];

			var qrRSBlock = function qrRSBlock(totalCount, dataCount) {
				var _this = {};
				_this.totalCount = totalCount;
				_this.dataCount = dataCount;
				return _this;
			};

			var _this = {};

			var getRsBlockTable = function getRsBlockTable(typeNumber, errorCorrectLevel) {

				switch (errorCorrectLevel) {
					case QRErrorCorrectLevel.L:
						return RS_BLOCK_TABLE[(typeNumber - 1) * 4 + 0];
					case QRErrorCorrectLevel.M:
						return RS_BLOCK_TABLE[(typeNumber - 1) * 4 + 1];
					case QRErrorCorrectLevel.Q:
						return RS_BLOCK_TABLE[(typeNumber - 1) * 4 + 2];
					case QRErrorCorrectLevel.H:
						return RS_BLOCK_TABLE[(typeNumber - 1) * 4 + 3];
					default:
						return undefined;
				}
			};

			_this.getRSBlocks = function (typeNumber, errorCorrectLevel) {

				var rsBlock = getRsBlockTable(typeNumber, errorCorrectLevel);

				if (typeof rsBlock == 'undefined') {
					throw new Error('bad rs block @ typeNumber:' + typeNumber + '/errorCorrectLevel:' + errorCorrectLevel);
				}

				var length = rsBlock.length / 3;

				var list = new Array();

				for (var i = 0; i < length; i += 1) {

					var count = rsBlock[i * 3 + 0];
					var totalCount = rsBlock[i * 3 + 1];
					var dataCount = rsBlock[i * 3 + 2];

					for (var j = 0; j < count; j += 1) {
						list.push(qrRSBlock(totalCount, dataCount));
					}
				}

				return list;
			};

			return _this;
		}();

		//---------------------------------------------------------------------
		// qrBitBuffer
		//---------------------------------------------------------------------

		var qrBitBuffer = function qrBitBuffer() {

			var _buffer = new Array();
			var _length = 0;

			var _this = {};

			_this.getBuffer = function () {
				return _buffer;
			};

			_this.getAt = function (index) {
				var bufIndex = Math.floor(index / 8);
				return (_buffer[bufIndex] >>> 7 - index % 8 & 1) == 1;
			};

			_this.put = function (num, length) {
				for (var i = 0; i < length; i += 1) {
					_this.putBit((num >>> length - i - 1 & 1) == 1);
				}
			};

			_this.getLengthInBits = function () {
				return _length;
			};

			_this.putBit = function (bit) {

				var bufIndex = Math.floor(_length / 8);
				if (_buffer.length <= bufIndex) {
					_buffer.push(0);
				}

				if (bit) {
					_buffer[bufIndex] |= 0x80 >>> _length % 8;
				}

				_length += 1;
			};

			return _this;
		};

		//---------------------------------------------------------------------
		// qr8BitByte
		//---------------------------------------------------------------------

		var qr8BitByte = function qr8BitByte(data) {

			var _mode = QRMode.MODE_8BIT_BYTE;
			var _data = data;
			var _bytes = qrcode.stringToBytes(data);

			var _this = {};

			_this.getMode = function () {
				return _mode;
			};

			_this.getLength = function (buffer) {
				return _bytes.length;
			};

			_this.write = function (buffer) {
				for (var i = 0; i < _bytes.length; i += 1) {
					buffer.put(_bytes[i], 8);
				}
			};

			return _this;
		};

		//=====================================================================
		// GIF Support etc.
		//

		//---------------------------------------------------------------------
		// byteArrayOutputStream
		//---------------------------------------------------------------------

		var byteArrayOutputStream = function byteArrayOutputStream() {

			var _bytes = new Array();

			var _this = {};

			_this.writeByte = function (b) {
				_bytes.push(b & 0xff);
			};

			_this.writeShort = function (i) {
				_this.writeByte(i);
				_this.writeByte(i >>> 8);
			};

			_this.writeBytes = function (b, off, len) {
				off = off || 0;
				len = len || b.length;
				for (var i = 0; i < len; i += 1) {
					_this.writeByte(b[i + off]);
				}
			};

			_this.writeString = function (s) {
				for (var i = 0; i < s.length; i += 1) {
					_this.writeByte(s.charCodeAt(i));
				}
			};

			_this.toByteArray = function () {
				return _bytes;
			};

			_this.toString = function () {
				var s = '';
				s += '[';
				for (var i = 0; i < _bytes.length; i += 1) {
					if (i > 0) {
						s += ',';
					}
					s += _bytes[i];
				}
				s += ']';
				return s;
			};

			return _this;
		};

		//---------------------------------------------------------------------
		// base64EncodeOutputStream
		//---------------------------------------------------------------------

		var base64EncodeOutputStream = function base64EncodeOutputStream() {

			var _buffer = 0;
			var _buflen = 0;
			var _length = 0;
			var _base64 = '';

			var _this = {};

			var writeEncoded = function writeEncoded(b) {
				_base64 += String.fromCharCode(encode(b & 0x3f));
			};

			var encode = function encode(n) {
				if (n < 0) {
					// error.
				} else if (n < 26) {
					return 0x41 + n;
				} else if (n < 52) {
					return 0x61 + (n - 26);
				} else if (n < 62) {
					return 0x30 + (n - 52);
				} else if (n == 62) {
					return 0x2b;
				} else if (n == 63) {
					return 0x2f;
				}
				throw new Error('n:' + n);
			};

			_this.writeByte = function (n) {

				_buffer = _buffer << 8 | n & 0xff;
				_buflen += 8;
				_length += 1;

				while (_buflen >= 6) {
					writeEncoded(_buffer >>> _buflen - 6);
					_buflen -= 6;
				}
			};

			_this.flush = function () {

				if (_buflen > 0) {
					writeEncoded(_buffer << 6 - _buflen);
					_buffer = 0;
					_buflen = 0;
				}

				if (_length % 3 != 0) {
					// padding
					var padlen = 3 - _length % 3;
					for (var i = 0; i < padlen; i += 1) {
						_base64 += '=';
					}
				}
			};

			_this.toString = function () {
				return _base64;
			};

			return _this;
		};

		//---------------------------------------------------------------------
		// base64DecodeInputStream
		//---------------------------------------------------------------------

		var base64DecodeInputStream = function base64DecodeInputStream(str) {

			var _str = str;
			var _pos = 0;
			var _buffer = 0;
			var _buflen = 0;

			var _this = {};

			_this.read = function () {

				while (_buflen < 8) {

					if (_pos >= _str.length) {
						if (_buflen == 0) {
							return -1;
						}
						throw new Error('unexpected end of file./' + _buflen);
					}

					var c = _str.charAt(_pos);
					_pos += 1;

					if (c == '=') {
						_buflen = 0;
						return -1;
					} else if (c.match(/^\s$/)) {
						// ignore if whitespace.
						continue;
					}

					_buffer = _buffer << 6 | decode(c.charCodeAt(0));
					_buflen += 6;
				}

				var n = _buffer >>> _buflen - 8 & 0xff;
				_buflen -= 8;
				return n;
			};

			var decode = function decode(c) {
				if (0x41 <= c && c <= 0x5a) {
					return c - 0x41;
				} else if (0x61 <= c && c <= 0x7a) {
					return c - 0x61 + 26;
				} else if (0x30 <= c && c <= 0x39) {
					return c - 0x30 + 52;
				} else if (c == 0x2b) {
					return 62;
				} else if (c == 0x2f) {
					return 63;
				} else {
					throw new Error('c:' + c);
				}
			};

			return _this;
		};

		//---------------------------------------------------------------------
		// gifImage (B/W)
		//---------------------------------------------------------------------

		var gifImage = function gifImage(width, height) {

			var _width = width;
			var _height = height;
			var _data = new Array(width * height);

			var _this = {};

			_this.setPixel = function (x, y, pixel) {
				_data[y * _width + x] = pixel;
			};

			_this.write = function (out) {

				//---------------------------------
				// GIF Signature

				out.writeString('GIF87a');

				//---------------------------------
				// Screen Descriptor

				out.writeShort(_width);
				out.writeShort(_height);

				out.writeByte(0x80); // 2bit
				out.writeByte(0);
				out.writeByte(0);

				//---------------------------------
				// Global Color Map

				// black
				out.writeByte(0x00);
				out.writeByte(0x00);
				out.writeByte(0x00);

				// white
				out.writeByte(0xff);
				out.writeByte(0xff);
				out.writeByte(0xff);

				//---------------------------------
				// Image Descriptor

				out.writeString(',');
				out.writeShort(0);
				out.writeShort(0);
				out.writeShort(_width);
				out.writeShort(_height);
				out.writeByte(0);

				//---------------------------------
				// Local Color Map

				//---------------------------------
				// Raster Data

				var lzwMinCodeSize = 2;
				var raster = getLZWRaster(lzwMinCodeSize);

				out.writeByte(lzwMinCodeSize);

				var offset = 0;

				while (raster.length - offset > 255) {
					out.writeByte(255);
					out.writeBytes(raster, offset, 255);
					offset += 255;
				}

				out.writeByte(raster.length - offset);
				out.writeBytes(raster, offset, raster.length - offset);
				out.writeByte(0x00);

				//---------------------------------
				// GIF Terminator
				out.writeString(';');
			};

			var bitOutputStream = function bitOutputStream(out) {

				var _out = out;
				var _bitLength = 0;
				var _bitBuffer = 0;

				var _this = {};

				_this.write = function (data, length) {

					if (data >>> length != 0) {
						throw new Error('length over');
					}

					while (_bitLength + length >= 8) {
						_out.writeByte(0xff & (data << _bitLength | _bitBuffer));
						length -= 8 - _bitLength;
						data >>>= 8 - _bitLength;
						_bitBuffer = 0;
						_bitLength = 0;
					}

					_bitBuffer = data << _bitLength | _bitBuffer;
					_bitLength = _bitLength + length;
				};

				_this.flush = function () {
					if (_bitLength > 0) {
						_out.writeByte(_bitBuffer);
					}
				};

				return _this;
			};

			var getLZWRaster = function getLZWRaster(lzwMinCodeSize) {

				var clearCode = 1 << lzwMinCodeSize;
				var endCode = (1 << lzwMinCodeSize) + 1;
				var bitLength = lzwMinCodeSize + 1;

				// Setup LZWTable
				var table = lzwTable();

				for (var i = 0; i < clearCode; i += 1) {
					table.add(String.fromCharCode(i));
				}
				table.add(String.fromCharCode(clearCode));
				table.add(String.fromCharCode(endCode));

				var byteOut = byteArrayOutputStream();
				var bitOut = bitOutputStream(byteOut);

				// clear code
				bitOut.write(clearCode, bitLength);

				var dataIndex = 0;

				var s = String.fromCharCode(_data[dataIndex]);
				dataIndex += 1;

				while (dataIndex < _data.length) {

					var c = String.fromCharCode(_data[dataIndex]);
					dataIndex += 1;

					if (table.contains(s + c)) {

						s = s + c;
					} else {

						bitOut.write(table.indexOf(s), bitLength);

						if (table.size() < 0xfff) {

							if (table.size() == 1 << bitLength) {
								bitLength += 1;
							}

							table.add(s + c);
						}

						s = c;
					}
				}

				bitOut.write(table.indexOf(s), bitLength);

				// end code
				bitOut.write(endCode, bitLength);

				bitOut.flush();

				return byteOut.toByteArray();
			};

			var lzwTable = function lzwTable() {

				var _map = {};
				var _size = 0;

				var _this = {};

				_this.add = function (key) {
					if (_this.contains(key)) {
						throw new Error('dup key:' + key);
					}
					_map[key] = _size;
					_size += 1;
				};

				_this.size = function () {
					return _size;
				};

				_this.indexOf = function (key) {
					return _map[key];
				};

				_this.contains = function (key) {
					return typeof _map[key] != 'undefined';
				};

				return _this;
			};

			return _this;
		};

		var createImgTag = function createImgTag(width, height, getPixel, alt) {

			var gif = gifImage(width, height);
			for (var y = 0; y < height; y += 1) {
				for (var x = 0; x < width; x += 1) {
					gif.setPixel(x, y, getPixel(x, y));
				}
			}

			var b = byteArrayOutputStream();
			gif.write(b);

			var base64 = base64EncodeOutputStream();
			var bytes = b.toByteArray();
			for (var i = 0; i < bytes.length; i += 1) {
				base64.writeByte(bytes[i]);
			}
			base64.flush();

			var img = '';
			img += '<img';
			img += ' src="';
			img += 'data:image/gif;base64,';
			img += base64;
			img += '"';
			img += ' width="';
			img += width;
			img += '"';
			img += ' height="';
			img += height;
			img += '"';
			if (alt) {
				img += ' alt="';
				img += alt;
				img += '"';
			}
			img += '/>';

			return img;
		};

		var createImgBase64 = function createImgBase64(width, height, getPixel) {

			var gif = gifImage(width, height);
			for (var y = 0; y < height; y += 1) {
				for (var x = 0; x < width; x += 1) {
					gif.setPixel(x, y, getPixel(x, y));
				}
			}

			var b = byteArrayOutputStream();
			gif.write(b);

			var base64 = base64EncodeOutputStream();
			var bytes = b.toByteArray();
			for (var i = 0; i < bytes.length; i += 1) {
				base64.writeByte(bytes[i]);
			}
			base64.flush();

			return base64.toString();
		};

		//---------------------------------------------------------------------
		// returns qrcode function.

		return qrcode;
	}();

	module.exports = qrcode;

/***/ },
/* 286 */
/***/ function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ },
/* 287 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__.p + "9d5f2ff38406c8ed6bcf3bb68aa156c3.jpg";

/***/ }
/******/ ]);