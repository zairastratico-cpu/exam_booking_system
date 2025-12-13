import { Container, Row, Col } from "react-bootstrap";
import { Link } from "react-router-dom";

import { FaPhone } from "react-icons/fa";

function BookFooter() {
  return (
    <footer className="footer-custom py-4">
      <Container>
        <Row>
          <Col xs={12} lg={4} className="text-center mb-4 mb-md-0">
            <img src="/white_logo.png" alt="CertiCod" className="footer-logo my-5 my-lg-3" />
            <p className="mb-1">BIESSE SOLUTION SRL</p>
            <p className="mb-1">Viale A. La falce, 85</p>
            <p className="mb-1">87040 San Lorenzo del Vallo (CS)</p>
            <p className="mb-3">P.IVA 03244770784</p>
            <a href="tel:09811848023" className="btn-phone align-middle">
              <FaPhone size={14} style={{ marginRight: "8px" }} />
              0981.1848023
            </a>
          </Col>

          {/* Colonna 2 - Su di Noi */}
          <Col xs={12} lg={4} className="text-center mb-4 mb-md-0">
            <h5 className="footer-title fs-2 my-4">Su di Noi</h5>
            <ul className="footer-links">
              <li>
                <Link to="/chi-siamo">Chi siamo</Link>
              </li>
              <li>
                <Link to="/privacy">Privacy/Cookie Policy</Link>
              </li>
              <li>
                <Link to="/termini">Termini e Condizioni</Link>
              </li>
              <li>
                <Link to="/accreditamento">Modulo Accreditamento Centri Affiliati</Link>
              </li>
              <li>
                <Link to="/contatti">Contatti</Link>
              </li>
            </ul>
          </Col>

          {/* Colonna 3 - Certi Cod FULL */}
          <Col xs={12} lg={4} className="text-center">
            <h5 className="footer-title  fs-2 my-4">Certi Cod FULL</h5>
            <ul className="footer-links">
              <li>
                <Link to="/programma">Programma Analitico d'Esame</Link>
              </li>
              <li>
                <Link to="/regolamento">Regolamento Esame</Link>
              </li>
              <li>
                <Link to="/procedura">Procedura Esame</Link>
              </li>
              <li>
                <Link to="/diritti">Diritti e Doveri del Candidato</Link>
              </li>
              <li>
                <Link to="/reclami">Reclami</Link>
              </li>
              <li>
                <Link to="/ricorsi">Ricorsi</Link>
              </li>
            </ul>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}

export default BookFooter;
