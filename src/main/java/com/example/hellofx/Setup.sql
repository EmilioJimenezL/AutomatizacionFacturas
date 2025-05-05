CREATE DATABASE IF NOT EXISTS Consultorio;
CREATE TABLE IF NOT EXISTS Consultorio.RegimenesFiscales
(
    ID_Regimen    VARCHAR(4) PRIMARY KEY,
    Texto_Regimen VARCHAR(128)
);
INSERT INTO Consultorio.RegimenesFiscales (ID_Regimen, Texto_Regimen)
SELECT *
FROM (SELECT '605' AS ID_Regimen, 'Sueldos y Salarios e Ingresos Asimilados a Salarios' AS Texto_Regimen
      UNION ALL
      SELECT '606', 'Arrendamiento'
      UNION ALL
      SELECT '608', 'Demás ingresos'
      UNION ALL
      SELECT '611', 'Ingresos por Dividendos (socios y accionistas)'
      UNION ALL
      SELECT '612', 'Personas Físicas con Actividades Empresariales y Profesionales'
      UNION ALL
      SELECT '614', 'Ingresos por intereses'
      UNION ALL
      SELECT '615', 'Régimen de los ingresos por obtención de premios'
      UNION ALL
      SELECT '616', 'Sin obligaciones fiscales'
      UNION ALL
      SELECT '621', 'Incorporación Fiscal'
      UNION ALL
      SELECT '622', 'Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras'
      UNION ALL
      SELECT '626', 'Régimen Simplificado de Confianza (RESICO)'
      UNION ALL
      SELECT '629', 'De los Regímenes Fiscales Preferentes y de las Empresas Multinacionales'
      UNION ALL
      SELECT '630', 'Enajenación de acciones en bolsa de valores'
      UNION ALL
      SELECT '601', 'General de Ley Personas Morales'
      UNION ALL
      SELECT '603', 'Personas Morales con Fines no Lucrativos'
      UNION ALL
      SELECT '607', 'Régimen de Enajenación o Adquisición de Bienes'
      UNION ALL
      SELECT '609', 'Consolidación'
      UNION ALL
      SELECT '620', 'Sociedades Cooperativas de Producción que optan por Diferir sus Ingresos'
      UNION ALL
      SELECT '623', 'Opcional para Grupos de Sociedades'
      UNION ALL
      SELECT '624', 'Coordinados'
      UNION ALL
      SELECT '628', 'Hidrocarburos') AS temp
WHERE NOT EXISTS (SELECT 1 FROM Consultorio.RegimenesFiscales LIMIT 1);

CREATE TABLE IF NOT EXISTS Consultorio.UsosCFDI
(
    ID_CFDI    VARCHAR(4) PRIMARY KEY,
    Texto_CFDI VARCHAR(128)
);
INSERT INTO Consultorio.UsosCFDI (ID_CFDI, Texto_CFDI)
SELECT *
FROM (SELECT 'G01' AS ID_CFDI, 'Adquisición de mercancías' AS Texto_CFDI
      UNION ALL
      SELECT 'G02', 'Devoluciones, descuentos o bonificaciones'
      UNION ALL
      SELECT 'G03', 'Gastos en general'
      UNION ALL
      SELECT 'I01', 'Construcciones'
      UNION ALL
      SELECT 'I02', 'Mobiliario y equipo de oficina por inversiones'
      UNION ALL
      SELECT 'I03', 'Equipo de transporte'
      UNION ALL
      SELECT 'I04', 'Equipo de cómputo y accesorios'
      UNION ALL
      SELECT 'I05', 'Dados, troqueles, moldes, matrices y herramental'
      UNION ALL
      SELECT 'I06', 'Comunicaciones telefónicas'
      UNION ALL
      SELECT 'I07', 'Comunicaciones satelitales'
      UNION ALL
      SELECT 'I08', 'Otra maquinaria y equipo'
      UNION ALL
      SELECT 'D01', 'Honorarios médicos, dentales y gastos hospitalarios'
      UNION ALL
      SELECT 'D02', 'Gastos médicos por incapacidad o discapacidad'
      UNION ALL
      SELECT 'D03', 'Gastos funerales'
      UNION ALL
      SELECT 'D04', 'Donativos'
      UNION ALL
      SELECT 'D05', 'Intereses reales efectivamente pagados por créditos hipotecarios (casa habitación)'
      UNION ALL
      SELECT 'D06', 'Aportaciones voluntarias al SAR'
      UNION ALL
      SELECT 'D07', 'Primas por seguros de gastos médicos'
      UNION ALL
      SELECT 'D08', 'Gastos de transportación escolar obligatoria'
      UNION ALL
      SELECT 'D09', 'Depósitos en cuentas para el ahorro, primas que tengan como base planes de pensiones'
      UNION ALL
      SELECT 'D10', 'Pagos por servicios educativos (colegiaturas)'
      UNION ALL
      SELECT 'P01', 'Por definir') AS temp
WHERE NOT EXISTS (SELECT 1 FROM Consultorio.UsosCFDI LIMIT 1);

CREATE TABLE IF NOT EXISTS Consultorio.Pacientes(
    ID_Paciente INT PRIMARY KEY AUTO_INCREMENT,
    Apellido VARCHAR(64) NOT NULL,
    Nombre VARCHAR(64) NOT NULL,
    NumContacto1 VARCHAR(13) NOT NULL,
    NumContacto2 VARCHAR(13),
    Correo VARCHAR(64)
);
CREATE TABLE IF NOT EXISTS Consultorio.PerfilesFiscales(
    ID_PerfilFiscal INT PRIMARY KEY AUTO_INCREMENT,
    Paciente INT,
    RazonSocial VARCHAR(64),
    RFC VARCHAR(14),
    CP VARCHAR(6),
    Regimen VARCHAR(4),
    FOREIGN KEY (Paciente) REFERENCES Consultorio.Pacientes(ID_Paciente),
    FOREIGN KEY (Regimen) REFERENCES Consultorio.RegimenesFiscales(ID_Regimen)
);
CREATE TABLE IF NOT EXISTS Consultorio.SolicitudesFacturas(
    ID_Solicitud INT PRIMARY KEY AUTO_INCREMENT,
    Perfil_Fiscal INT,
    UsoCFDI VARCHAR(4),
    Monto VARCHAR(8),
    Concepto VARCHAR(128),
    FormaDePago VARCHAR(32),
    Estado VARCHAR(16),
    FOREIGN KEY (Perfil_Fiscal) REFERENCES Consultorio.PerfilesFiscales(ID_PerfilFiscal),
    FOREIGN KEY (UsoCFDI) REFERENCES Consultorio.UsosCFDI(ID_CFDI)
);
DROP VIEW IF EXISTS SolicitudCorreo;
CREATE VIEW SolicitudCorreo AS
SELECT PerfilesFiscales.RazonSocial    AS Razon,
       PerfilesFiscales.RFC            AS RFC,
       PerfilesFiscales.CP             AS CP,
       UsosCFDI.Texto_CFDI             AS CFDI,
       RegimenesFiscales.Texto_Regimen AS Regimen,
       SolicitudesFacturas.Monto       AS Monto,
       SolicitudesFacturas.Concepto    AS Concepto
FROM SolicitudesFacturas
         JOIN PerfilesFiscales ON Perfil_Fiscal = ID_PerfilFiscal
         JOIN UsosCFDI ON UsoCFDI = ID_CFDI
         JOIN RegimenesFiscales ON Regimen = ID_Regimen
WHERE SolicitudesFacturas.Estado = 'Pendiente';

DELIMITER //
DROP PROCEDURE IF EXISTS BuscarPacienteApellido//
CREATE PROCEDURE BuscarPacienteApellido(IN InputApellido VARCHAR(64))
BEGIN
    SELECT * FROM Pacientes WHERE Apellido LIKE CONCAT('%', InputApellido, '%');
END //

DROP PROCEDURE IF EXISTS BuscarPacienteNombre//
CREATE PROCEDURE BuscarPacienteNombre(IN InputNombre VARCHAR(64))
BEGIN
    SELECT * FROM Pacientes WHERE Nombre LIKE CONCAT('%', InputNombre, '%');
END //

DROP PROCEDURE IF EXISTS BuscarPacienteNumero//
CREATE PROCEDURE BuscarPacienteNumero(IN InputContacto VARCHAR(13))
BEGIN
    SELECT *
    FROM Pacientes
    WHERE NumContacto1 LIKE CONCAT('%', InputContacto, '%')
       OR NumContacto2 LIKE CONCAT('%', InputContacto, '%');
END //

DROP PROCEDURE IF EXISTS BuscarPacienteCorreo//
CREATE PROCEDURE BuscarPacienteCorreo(IN InputCorreo VARCHAR(64))
BEGIN
    SELECT * FROM Pacientes WHERE Correo LIKE CONCAT('%', InputCorreo, '%');
END //
DELIMITER ;

DROP VIEW IF EXISTS VistaGUI;
CREATE VIEW VistaGUI AS
SELECT SF.ID_Solicitud,
       P.Nombre,
       P.Apellido,
       P.NumContacto1   AS NumContacto,
       P.Correo,
       PF.RazonSocial,
       PF.RFC,
       RF.Texto_Regimen AS Regimen,
       UC.Texto_CFDI    AS CFDI,
       SF.Monto,
       SF.Concepto,
       SF.FormaDePago,
       SF.Estado
FROM Pacientes P
         JOIN PerfilesFiscales PF ON P.ID_Paciente = PF.Paciente
         JOIN RegimenesFiscales RF ON PF.Regimen = RF.ID_Regimen
         JOIN SolicitudesFacturas SF ON PF.ID_PerfilFiscal = SF.Perfil_Fiscal
         JOIN UsosCFDI UC ON SF.UsoCFDI = UC.ID_CFDI;

DROP VIEW IF EXISTS PerfilesFiscalesView;
CREATE VIEW PerfilesFiscalesView AS
SELECT pf.ID_PerfilFiscal,
       pf.RFC,
       pf.RazonSocial,
       pf.CP,
       p.Nombre         AS Paciente,
       rf.Texto_Regimen AS Regimen
FROM PerfilesFiscales pf
         LEFT JOIN Pacientes p ON pf.Paciente = p.Id_Paciente
         LEFT JOIN RegimenesFiscales rf ON pf.Regimen = rf.Id_Regimen;

DELIMITER //
DROP PROCEDURE IF EXISTS BuscarPacientePorPerfilFiscal//
CREATE PROCEDURE BuscarPacientePorPerfilFiscal(IN ID_Perfil INT)
BEGIN
    SELECT P.*
    FROM Pacientes P
             INNER JOIN PerfilesFiscales PF ON P.ID_Paciente = PF.Paciente
    WHERE PF.ID_PerfilFiscal = ID_Perfil;
END //

DROP PROCEDURE IF EXISTS GetPerfilesFiscalesPorPaciente//
CREATE PROCEDURE GetPerfilesFiscalesPorPaciente(IN ID_Paciente INT)
BEGIN
    SELECT pf.ID_PerfilFiscal,
           pf.RazonSocial,
           pf.RFC,
           pf.CP,
           rf.Texto_Regimen AS Regimen,
           p.Apellido,
           p.Nombre
    FROM PerfilesFiscales pf
             LEFT JOIN RegimenesFiscales rf ON pf.Regimen = rf.ID_Regimen
             LEFT JOIN Pacientes p ON pf.Paciente = p.ID_Paciente
    WHERE pf.Paciente = ID_Paciente;
END //
DELIMITER ;