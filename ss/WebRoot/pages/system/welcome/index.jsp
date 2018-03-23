<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="stylesheet" href="css/bootstrap.css" media="screen">
    <link rel="stylesheet" href="skins/eden.css" media="screen">
    <link rel="stylesheet" href="style.css" media="screen">
    <link href="css/animate.css" rel="stylesheet">

</head>
<body   data-spy="scroll" data-target="#topnav"  id="top">

    <section class="header-area-home" id="header-area-home" style="margin-bottom:0;margin-bottom:0;height: 100%;width: 100%">
					<!-- 
        <div id="main-nav-container">
            <div  class="container">
            <nav class="navbar navbar-eden" id="topnav">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#main-menu">
                            <span class="sr-only">Toggle navigation</span>
                            <i class="tn-menu"></i>
                        </button>
                        <a class="navbar-brand" href="#"><img src="imgs/optimus-logo.png" /></a>
                    </div>
                    <div class="collapse navbar-collapse" id="main-menu">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#top">Home</a></li>
                            <li><a href="#why-us">Why Us</a></li>
                            <li><a href="#out-team">Our Team</a></li>
                            <li><a href="#client-reviews">Reviews</a></li>
                            <li><a href="#pricing">Pricing</a></li>
                            <li><a href="#curious">Contact</a></li>

                        </ul>

                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#search"><i class="tn-search"></i></a></li>
                        </ul>
                    </div>
                </div>
            </nav>
            </div>
        </div>
					 -->
        <div id="featured-slider-container">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div id="featured-slider" class="carousel slide" data-ride="carousel">
                            

                            <!-- Wrapper for slides -->
                            <div class="carousel-inner" role="listbox">
                                <div class="item active" id="slide-2" style="padding:80px 0 200px">
                                    <img src='imgs/slider-200.png' alt='' class='featured-slide inv' data-animation = 'animated zoomIn' data-delay = '0.3s' />
                                	<!-- 
                                    <img src='imgs/slider-200.png' alt='' class='featured-slide inv' data-animation = 'animated zoomIn' data-delay = '0.3s' />
                                    <img src='imgs/slider-2.png' alt='' class='featured-slide featured-slide-1 inv' data-animation = 'animated zoomIn' data-delay = '0.7s' />
                                	 -->
                                    <div class="carousel-caption" style="top:10%">
                                        <span class="lead slide-cat inv"  data-animation="animated zoomIn"><a href="#" rel="category">登录成功</a></span>
                                        <h2 class="slide-title inv"  data-animation="animated fadeInUp" data-duration="2s" data-delay="1s">亲脉后台管理系</h2>
                                        <div class="slide-excerpt inv" data-animation="animated fadeInUp" data-duration="3s" data-delay="2s">
                                            <ul class="row">
                                                <%--
                                                <li class="col-lg-6"  data-delay = '2s'>说明2</li>
                                                <li class="col-lg-6" data-animation = 'animated zoomIn' data-delay = '2.1s'>说明2</li>
                                                <li class="col-lg-6" data-animation = 'animated zoomIn' data-delay = '2.2s'>说明2</li>
                                                <li class="col-lg-6" data-animation = 'animated zoomIn' data-delay = '2.3s'>说明2</li>
                                                <li class="col-lg-6" data-animation = 'animated zoomIn' data-delay = '2.4s'>说明2</li>
                                                <li class="col-lg-6" data-animation = 'animated zoomIn' data-delay = '2.5s'>说明2</li>
                                                 --%>

                                            </ul>
                                        </div>
                                        <div class="meta">
                                            <button class="btn btn-primary inv slide-btn btn-lg"  data-animation="animated fadeInUp" data-delay="2s" data-duration="2s">欢迎使用 <i class="tn-arrow-right"></i></button>
                                        </div>

                                    </div>
                                </div>
                               

                            </div>


                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>


<script src="js/jquery-2.1.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.sticky.js"></script>
<script src="js/wow.min.js"></script>
<script src="js/site.js"></script>

</body>
</html>
