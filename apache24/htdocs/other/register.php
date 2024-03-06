<?php  
	$con=mysqli_connect("localhost","root","1234","everyhealth");
	$nid = $_POST['nid'];
	$npw = $_POST['npw'];
	$wei = 0;
	$hei = 0;
	
	
	

	if(mysqli_connect_error($con))
		echo "Failied to connect : " .mysqli_connect_error();

	$max_idx=mysqli_query($con,"select max(user_idx)+1 from user_login");	
	$max_idx_info=mysqli_fetch_array($max_idx);
	$max_idx_info[0]=(int)$max_idx_info[0];
	#$check=mysqli_query($con, "select * from user_login where id = '$nid'and passwd='$npw'");
	#if(!$check){
	$input=mysqli_query($con, "insert into user_login (user_idx,id,passwd,height,weight) values ($max_idx_info[0],'$nid','$npw',$hei,$wei)");
	$result=mysqli_query($con, "select * from user_login where id = '$nid'and passwd='$npw'");

	$rowCnt= mysqli_num_rows($result);
   	$arr= array();
 
  	 for($i=0;$i<$rowCnt;$i++){
     	 	$row= mysqli_fetch_array($result, MYSQLI_ASSOC);
	        	$arr[$i]= $row;      
    	}
 
  	 
      	$jsonData=json_encode(array("webnautes"=>$arr), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
      	echo "$jsonData";
 	
   	mysqli_close($con);
	#}
	#mysqli_close($con);
?>  