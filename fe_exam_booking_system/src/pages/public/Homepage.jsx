import { Container, Row, Col } from "react-bootstrap";
import BookButton from "../../components/BookButton";
import BookDropdown from "../../components/BookDropdown";
import BookTable from "../../components/BookTable";

function Homepage() {
  return (
    <Container fluid className="homepage-main">
      <Row className="align-items-center justify-content-center">
        <Col xs={12} className="section d-flex justify-content-center align-items-center gap-3 py-3">
          <BookDropdown className="d-inline-block" />
          <BookButton to="/sessions" variant="primary" className="text-uppercase d-inline-block">
            Filtra
          </BookButton>
        </Col>
      </Row>
      <Row className="align-items-center justify-content-center my-5">
        <Col xs={12} md={10} lg={8} xl={6}>
          <h1 className="text-center py-5">Sessioni Disponibili</h1>
          <BookTable></BookTable>
        </Col>
      </Row>
    </Container>
  );
}

export default Homepage;
