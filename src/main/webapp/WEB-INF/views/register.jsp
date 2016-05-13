<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Register</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link href="../../resources/css/registerdefault.css" rel="stylesheet" type="text/css" />
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
 
</div>

<div id="content">
  <div id="main">

	  <div id="message">${message}</div>
  
  <div id="form_wrapper" class="form_wrapper">
					<form class="register active"  method="post" action="registration">
						<h3>Register</h3>
						<div class="column">
							<div>
								<label>First Name:</label>
								<input type="text" name="firstName"/>
								<span class="error">This is an error</span>
							</div>
							<div>
								<label>Last Name:</label>
								<input type="text" name="lastName" />
								<span class="error">This is an error</span>
							</div>
						
						</div>
						<div class="column">
							<div>
								<label>Login:</label>
								<input type="text" name="userLogin"/>
								<span class="error">This is an error</span>
							</div>
							
							<div>
								<label>Password:</label>
								<input type="password" name="userPassword"/>
								<span class="error">This is an error</span>
							</div>
						</div>
						<div class="bottom">
							<input type="submit" value="Register" />
							<a href="/index">You have an account already? Log in here</a>
							<div class="clear"></div>
						</div>
					</form>
				</div>
   
    
  </div>
  
</div>
<div id="footer">
  <p id="legal">Copyright &copy; 2007 Enlight. All Rights Reserved. Designed by <a rel="nofollow" href="#">Free DataBase of Knowledge</a>.</p>
  <p id="links"><a href="#">Home</a> | <a href="#">Terms of Use</a></p>
</div>
</body>
</html>