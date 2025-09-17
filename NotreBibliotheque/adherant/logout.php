<?php
 session_start();
 session_destroy();
 unset($_SESSION['id']);
 unset($_SESSION['nom']);
 unset($_SESSION['prenom']);
 unset($_SESSION['username']);
 header('Location: index.html');

?>