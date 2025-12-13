import { Navbar, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

function BookNavbar() {
  return (
    <Navbar expand="lg" className="navbar-custom py-3">
      <Container fluid>
        <Navbar.Brand as={Link} to="/" className="mx-auto">
          <img src="/header_logo.png" height="80" alt="CertiCod Logo" />
        </Navbar.Brand>
      </Container>
    </Navbar>
  );
}

export default BookNavbar;
