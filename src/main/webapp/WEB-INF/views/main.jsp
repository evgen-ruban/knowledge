<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>BaseOfKnowledge</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <link href="../../resources/css/default.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="header">
  <div id="topmenu">
    <ul>
      <li><a href="#" id="topmenu1" accesskey="1">Home</a></li>
      <li><a href="#" id="topmenu2" accesskey="2">Contact</a></li>
      <li><a href="#" id="topmenu3" accesskey="3">Sitemap</a></li>
    </ul>
  </div>
  <div id="logo">
    <h1><a href="#">base of knowledge</a></h1>
    <h2><a href="#">you'll be smarter</a></h2>
  </div>
</div>
<div id="menu">
  <ul>
    <li class="first"><a href="#">About Us</a></li>
    <li><a href="#">Services</a></li>
    <li><a href="#">Support</a></li>
  </ul>
</div>
<div id="content">
  <div id="main">
    <div id="welcome">
      <h2>Welcome ${user.name} to Base of Knowledge!</h2>
      <p><strong>Now you can make your question</strong></p>
      <div id="questionBox">
        <form id="formQuestion" method="post" action="question">
          <fieldset>
            <label id="inputquestion">Make Your Question</label>
            <p><textarea name="content" id="textarea"></textarea></p>
            <p><input id="inputsub" type="submit" value="Make Your Question" /></p>
          </fieldset>
        </form>
      </div>
	  
	  <div id="allquestion">
        <form method="post" action="watchquestion">
          <fieldset>
            <label id="inputallquestion">All your question on our site</label>
		    <div id="divSubWatch"><p><input type="submit" id="inputsubWatch" value="Watch All" /></p></div>
        </fieldset>
      </form>
    </div>
    </div>

  </div>
  <div id="sidebar">
    <div id="logout" class="boxed">
      <h2 class="title">Client Account</h2>
      <div class="content">
        <form id="form1" method="get" action="logout">
          <p class="helloUser">Hello User!!!</p>
          <fieldset>
            <legend>Logout</legend>
            <input id="logoutsubmit1" type="submit" name="inputsubmit1" value="Logout" />
          </fieldset>
        </form>
      </div>
    </div>
    <div id="updates" class="boxed">
      <h2 class="title">Recent Updates</h2>
      <div class="content">
        <ul>
          <li>
            <h3>March 5, 2007</h3>
            <p><a href="#">Some News&#8230;</a></p>
          </li>
          <li>
            <h3>March 3, 2007</h3>
            <p><a href="#">Some News&#8230;</a></p>
          </li>
          <li>
            <h3>February 28, 2007</h3>
            <p><a href="#">Some News&#8230;</a></p>
          </li>
          <li>
            <h3>February 25, 2007</h3>
            <p><a href="#">Some News&#8230;</a></p>
          </li>
        </ul>
      </div>
    </div>
    <div id="partners" class="boxed">
      <h2 class="title">Partners</h2>
      <div class="content">
        <ul>
          <li><a href="#">Donec Dictum Metus</a></li>
          <li><a href="#">Etiam Rhoncus Volutpat</a></li>
          <li><a href="#">Integer Gravida Nibh</a></li>
          <li><a href="#">Maecenas Luctus Lectus</a></li>
          <li><a href="#">Mauris Vulputate Dolor</a></li>
          <li><a href="#">Nulla Luctus Eleifend</a></li>
          <li><a href="#">Posuere Augue Sit Nisl</a></li>
        </ul>
      </div>
    </div>
  </div>
</div>
<div id="footer">
  <p id="legal">Copyright &copy; 2007 Enlight. All Rights Reserved. Designed by <a rel="nofollow" target="_blank" href="#">Free DataBase of Knowledge</a>.</p>
  <p id="links"><a href="#">Home</a> | <a href="#">Terms of Use</a></p>
</div>
</body>
</html>