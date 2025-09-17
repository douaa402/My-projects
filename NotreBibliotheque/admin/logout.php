<?php
 session_start();
 session_destroy();
 unset($_SESSION['idB']);
 unset($_SESSION['nomB']);
 unset($_SESSION['prenomB']);
 header('Location: index.html');

?>