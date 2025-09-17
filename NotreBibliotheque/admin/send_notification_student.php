<?php
session_start();
include "header.phtml";
include "dbconnect.php";
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
  echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
       window.location.href = 'loginA.php';</script>";
  exit();
}
?>

<br><br><br>
<div class="container">
  <h2>Envoyer un Message à l'Étudiant</h2>
  <hr>

  <!-- Form for sending a message -->
  <form class="col-lg-6" action="" method="post" enctype="multipart/form-data">
    <table class="table table-bordered">
      <tr>
        <td>
          <select class="form-control" name="username" >
            <?php
            // Correct PDO query to fetch students from the correct table
            $stmt = $pdo->query("SELECT * FROM `inscription-adhérent`"); // Ensure this is the correct table name
            while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
            ?>
              <option value="<?php echo htmlspecialchars($row["username"]); ?>">
                <?php echo htmlspecialchars($row["username"]); ?>
              </option>
            <?php
            }
            ?>
          </select>
        </td>
      </tr>
      <tr>
        <td>
          <input type="text" class="form-control" name="title" placeholder="Entrez un titre" required>
        </td>
      </tr>
      <tr>
        <td>
          Message : <br>
          <textarea name="msg" class="form-control" placeholder="Entrez votre message ici" required></textarea>
        </td>
      </tr>
      <tr>
        <td>
          <input type="submit" name="submit" value="Envoyer le message" class="btn btn-primary">
        </td>
      </tr>
    </table>
  </form>

  <?php
  if (isset($_POST["submit"])) {
      // Validate form inputs to avoid SQL injection and empty fields
      if (!empty($_POST['username']) && !empty($_POST['title']) && !empty($_POST['msg'])) {
          
          // Sanitize the inputs (important for preventing XSS)
          $username = htmlspecialchars($_POST['username']); // Assign properly to $username
          $title = htmlspecialchars($_POST['title']);
          $msg = htmlspecialchars($_POST['msg']);
          
          // Prepare the SQL query securely using PDO
          $stmt = $pdo->prepare("INSERT INTO messages (usernameB, username, title, msg, read1) 
                                 VALUES (:usernameB, :username, :title, :msg, 'n')");
          
          // Execute the query with bound parameters
          $stmt->execute([
              ':usernameB' => $_SESSION['usernameB'],  // Assuming 'usernameB' is stored in session
              ':username' => $username,               // Correctly pass the $username variable
              ':title' => $title,
              ':msg' => $msg
          ]);

          // Success alert
          echo "<script>alert('Message envoyé avec succès !');</script>";
      } else {
          // Error message for empty fields
          echo "<script>alert('Veuillez remplir tous les champs obligatoires.');</script>";
      }
  }
  ?>
</div>

<?php
include "footer.phtml";
?>


