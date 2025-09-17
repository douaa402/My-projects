
<?php
session_start();
include "dbconnect.php"; 
include "header.phtml";

// Check if the user is authenticated as an admin
if (!isset($_SESSION["idB"]) || empty($_SESSION["idB"])) {
    echo "<script>alert('Veuillez vous connecter en tant qu\'administrateur.');
         window.location.href = 'loginA.php';</script>";
    exit();
}

?>

<br><br>
<div class="container">
    <h2>Ajouter les informations du livre</h2>
    <hr>

    <form class="col-lg-6" action="" method="post" enctype="multipart/form-data">
        <table class="table table-bordered">
            <tr>
                <td><input type="text" class="form-control" placeholder="Nom du livre" required name="book_name"></td>
            </tr>
            <tr>
                <td>Image du livre<input type="file" class="form-control" name="f1" ></td>
            </tr>
            <tr>
                <td><input type="text" class="form-control" name="book_author_name" placeholder="Auteur du livre" required></td>
            </tr>
            <tr>
                <td><input type="text" class="form-control" name="book_publication_name" placeholder="Nom de la publication" required></td>
            </tr>
            <tr>
                <td><input type="date" class="form-control" name="book_purchase_date" placeholder="Date d'achat" required></td>
            </tr>
            <tr>
                <td><input type="number" step="0.01" class="form-control" name="book_price" placeholder="Prix du livre (dinars)" required></td>
            </tr>
            <tr>
                <td><input type="number" class="form-control" name="book_quantity" placeholder="Quantité totale" required></td>
            </tr>
            <tr>
                <td><input type="number" class="form-control" name="available_quantity" placeholder="Quantité disponible" required></td>
            </tr>
            <tr>
                <td>
                    <button type="submit" name="submit" class="btn btn-primary" style="width: 100%;">Ajouter le livre</button>
                </td>
            </tr>
        </table>
    </form>
</div>

<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['submit'])) {
    try {
        // Validate and sanitize input data
        $bookName = isset($_POST['book_name']) ? htmlspecialchars(trim($_POST['book_name'])) : null;
        $authorName = isset($_POST['book_author_name']) ? htmlspecialchars(trim($_POST['book_author_name'])) : null;
        $pubName = isset($_POST['book_publication_name']) ? htmlspecialchars(trim($_POST['book_publication_name'])) : null;
        $purchaseDate = isset($_POST['book_purchase_date']) ? htmlspecialchars(trim($_POST['book_purchase_date'])) : null;
        $price = isset($_POST['book_price']) ? floatval($_POST['book_price']) : 0.0;
        $quantity = isset($_POST['book_quantity']) ? intval($_POST['book_quantity']) : 0;
        $availableQuantity = isset($_POST['available_quantity']) ? intval($_POST['available_quantity']) : 0;

        // Check if mandatory fields are filled
        if (!$bookName || !$authorName || !$pubName || !$purchaseDate || !$price || !$quantity || !$availableQuantity) {
            throw new Exception("Tous les champs sont obligatoires.");
        }


        // Debug: Check form values
        // var_dump($_POST);

        // Handle file upload
        if (!empty($_FILES['f1']['name'])) {
            // Check for upload errors
            if ($_FILES['f1']['error'] !== UPLOAD_ERR_OK) {
                throw new Exception("Erreur lors de l'upload du fichier.");
            }

            // Validate file type and size
            $allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
            if (!in_array($_FILES['f1']['type'], $allowedTypes)) {
                throw new Exception("Type de fichier non autorisé. Seules les images (JPEG, PNG, GIF) sont autorisées.");
            }
            if ($_FILES['f1']['size'] > 2 * 1024 * 1024) { // Max size: 2MB
                throw new Exception("La taille du fichier dépasse 2 Mo.");
            }

            // Generate unique file name
            $tm = md5(time());
            $fileName = basename($_FILES['f1']['name']);
            $destination = "./bookImage/" . $tm . "_" . $fileName;

            // Move uploaded file
            if (move_uploaded_file($_FILES['f1']['tmp_name'], $destination)) {
                // Insert book details into the database
                $sql = "INSERT INTO addbook (book_name, book_image,book_author_name, book_publication_name, book_purchase_date, book_price,book_quantity,available_quantity, admin_id)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                $query = $pdo->prepare($sql);

                if ($query->execute([
                    $bookName,
                    $destination,
                    $authorName,
                    $pubName,
                    $purchaseDate,
                    $price,
                    $quantity,
                    $availableQuantity,
                    $_SESSION["idB"] // Admin ID from session
                ])) {
                    echo "<script>alert('Le livre a été ajouté avec succès.');
                          window.location.href = 'addbook.php';</script>";
                } else {
                    throw new Exception("Échec de l'ajout du livre dans la base de données.");
                }
            } else {
                throw new Exception("Erreur lors de l'enregistrement de l'image.");
            }
        } else {
            throw new Exception("Veuillez ajouter une image pour le livre.");
        }
    } catch (Exception $e) {
        echo "<div class='alert alert-danger' role='alert' style='text-align: center;'>Erreur: " . $e->getMessage() . "</div>";
    }
}
?>

<?php include "footer.phtml"; ?>
