package com.hms.config;

import com.hms.entity.Admin;
import com.hms.entity.Bill;
import com.hms.entity.Doctor;
import com.hms.entity.Patient;
import com.hms.entity.Appointment;
import com.hms.service.BillService;
import com.hms.service.DoctorService;
import com.hms.service.PatientService;
import com.hms.service.UserService;
import com.hms.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private BillService billService;

    @Override
    public void run(String... args) throws Exception {
        loadInitialData();
    }

    private void loadInitialData() {
        // Create default admin
        if (!userService.existsByUsername("admin")) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@hospital.com");
            admin.setFullName("Hospital Administrator");
            admin.setPhoneNumber("1234567890");
            admin.setDepartment("Administration");
            admin.setEmployeeId("ADM001");
            admin.setAdminLevel(Admin.AdminLevel.SUPER_ADMIN);
            userService.saveUser(admin);
        }

        // Create sample doctors
        if (!doctorService.findByUsername("dr.smith").isPresent()) {
            Doctor doctor1 = new Doctor();
            doctor1.setUsername("dr.smith");
            doctor1.setPassword("doctor123");
            doctor1.setEmail("dr.smith@hospital.com");
            doctor1.setFullName("Dr. John Smith");
            doctor1.setPhoneNumber("9876543210");
            doctor1.setSpecialization("Cardiology");
            doctor1.setLicenseNumber("LIC001");
            doctor1.setExperienceYears(10);
            doctor1.setQualifications("MBBS, MD - Cardiology");
            doctor1.setConsultationFee("₹800");
            doctor1.setWorkingHours("9:00 AM - 5:00 PM");
            doctorService.saveDoctor(doctor1);
        }

        if (!doctorService.findByUsername("dr.johnson").isPresent()) {
            Doctor doctor2 = new Doctor();
            doctor2.setUsername("dr.johnson");
            doctor2.setPassword("doctor123");
            doctor2.setEmail("dr.johnson@hospital.com");
            doctor2.setFullName("Dr. Sarah Johnson");
            doctor2.setPhoneNumber("9876543211");
            doctor2.setSpecialization("Neurology");
            doctor2.setLicenseNumber("LIC002");
            doctor2.setExperienceYears(8);
            doctor2.setQualifications("MBBS, MD - Neurology");
            doctor2.setConsultationFee("₹1000");
            doctor2.setWorkingHours("10:00 AM - 6:00 PM");
            doctorService.saveDoctor(doctor2);
        }

        if (!doctorService.findByUsername("dr.williams").isPresent()) {
            Doctor doctor3 = new Doctor();
            doctor3.setUsername("dr.williams");
            doctor3.setPassword("doctor123");
            doctor3.setEmail("dr.williams@hospital.com");
            doctor3.setFullName("Dr. Michael Williams");
            doctor3.setPhoneNumber("9876543212");
            doctor3.setSpecialization("Orthopedics");
            doctor3.setLicenseNumber("LIC003");
            doctor3.setExperienceYears(12);
            doctor3.setQualifications("MBBS, MS - Orthopedics");
            doctor3.setConsultationFee("₹750");
            doctor3.setWorkingHours("8:00 AM - 4:00 PM");
            doctorService.saveDoctor(doctor3);
        }

        // Add Indian doctors with Indian names and rupee currency
        if (!doctorService.findByUsername("dr.sharma").isPresent()) {
            Doctor doctor4 = new Doctor();
            doctor4.setUsername("dr.sharma");
            doctor4.setPassword("doctor123");
            doctor4.setEmail("dr.sharma@hospital.com");
            doctor4.setFullName("Dr. Rajesh Sharma");
            doctor4.setPhoneNumber("9123456789");
            doctor4.setSpecialization("General Medicine");
            doctor4.setLicenseNumber("LIC004");
            doctor4.setExperienceYears(15);
            doctor4.setQualifications("MBBS, MD - Internal Medicine");
            doctor4.setConsultationFee("₹600");
            doctor4.setWorkingHours("9:00 AM - 6:00 PM");
            doctor4.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor4);
        }

        if (!doctorService.findByUsername("dr.patel").isPresent()) {
            Doctor doctor5 = new Doctor();
            doctor5.setUsername("dr.patel");
            doctor5.setPassword("doctor123");
            doctor5.setEmail("dr.patel@hospital.com");
            doctor5.setFullName("Dr. Priya Patel");
            doctor5.setPhoneNumber("9234567890");
            doctor5.setSpecialization("Gynecology");
            doctor5.setLicenseNumber("LIC005");
            doctor5.setExperienceYears(10);
            doctor5.setQualifications("MBBS, MS - Obstetrics & Gynecology");
            doctor5.setConsultationFee("₹900");
            doctor5.setWorkingHours("10:00 AM - 4:00 PM");
            doctor5.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor5);
        }

        if (!doctorService.findByUsername("dr.singh").isPresent()) {
            Doctor doctor6 = new Doctor();
            doctor6.setUsername("dr.singh");
            doctor6.setPassword("doctor123");
            doctor6.setEmail("dr.singh@hospital.com");
            doctor6.setFullName("Dr. Amanjeet Singh");
            doctor6.setPhoneNumber("9345678901");
            doctor6.setSpecialization("Pediatrics");
            doctor6.setLicenseNumber("LIC006");
            doctor6.setExperienceYears(8);
            doctor6.setQualifications("MBBS, MD - Pediatrics, Fellowship in Neonatology");
            doctor6.setConsultationFee("₹700");
            doctor6.setWorkingHours("8:00 AM - 3:00 PM");
            doctor6.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor6);
        }

        if (!doctorService.findByUsername("dr.gupta").isPresent()) {
            Doctor doctor7 = new Doctor();
            doctor7.setUsername("dr.gupta");
            doctor7.setPassword("doctor123");
            doctor7.setEmail("dr.gupta@hospital.com");
            doctor7.setFullName("Dr. Anita Gupta");
            doctor7.setPhoneNumber("9456789012");
            doctor7.setSpecialization("Dermatology");
            doctor7.setLicenseNumber("LIC007");
            doctor7.setExperienceYears(12);
            doctor7.setQualifications("MBBS, MD - Dermatology, Venereology & Leprosy");
            doctor7.setConsultationFee("₹800");
            doctor7.setWorkingHours("11:00 AM - 7:00 PM");
            doctor7.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor7);
        }

        if (!doctorService.findByUsername("dr.kumar").isPresent()) {
            Doctor doctor8 = new Doctor();
            doctor8.setUsername("dr.kumar");
            doctor8.setPassword("doctor123");
            doctor8.setEmail("dr.kumar@hospital.com");
            doctor8.setFullName("Dr. Vikash Kumar");
            doctor8.setPhoneNumber("9567890123");
            doctor8.setSpecialization("ENT");
            doctor8.setLicenseNumber("LIC008");
            doctor8.setExperienceYears(9);
            doctor8.setQualifications("MBBS, MS - ENT (Otorhinolaryngology)");
            doctor8.setConsultationFee("₹650");
            doctor8.setWorkingHours("9:30 AM - 5:30 PM");
            doctor8.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor8);
        }

        if (!doctorService.findByUsername("dr.reddy").isPresent()) {
            Doctor doctor9 = new Doctor();
            doctor9.setUsername("dr.reddy");
            doctor9.setPassword("doctor123");
            doctor9.setEmail("dr.reddy@hospital.com");
            doctor9.setFullName("Dr. Lakshmi Reddy");
            doctor9.setPhoneNumber("9678901234");
            doctor9.setSpecialization("Ophthalmology");
            doctor9.setLicenseNumber("LIC009");
            doctor9.setExperienceYears(14);
            doctor9.setQualifications("MBBS, MS - Ophthalmology, Fellowship in Retina");
            doctor9.setConsultationFee("₹950");
            doctor9.setWorkingHours("8:30 AM - 4:30 PM");
            doctor9.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor9);
        }

        if (!doctorService.findByUsername("dr.agarwal").isPresent()) {
            Doctor doctor10 = new Doctor();
            doctor10.setUsername("dr.agarwal");
            doctor10.setPassword("doctor123");
            doctor10.setEmail("dr.agarwal@hospital.com");
            doctor10.setFullName("Dr. Sanjay Agarwal");
            doctor10.setPhoneNumber("9789012345");
            doctor10.setSpecialization("Psychiatry");
            doctor10.setLicenseNumber("LIC010");
            doctor10.setExperienceYears(11);
            doctor10.setQualifications("MBBS, MD - Psychiatry, Diploma in Psychological Medicine");
            doctor10.setConsultationFee("₹1200");
            doctor10.setWorkingHours("2:00 PM - 8:00 PM");
            doctor10.setAvailableForConsultation(true);
            doctorService.saveDoctor(doctor10);
        }

        // Create sample patient
        if (!patientService.findByUsername("patient1").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("patient1");
            patient.setPassword("patient123");
            patient.setEmail("patient1@email.com");
            patient.setFullName("Alice Brown");
            patient.setPhoneNumber("5555555555");
            patient.setDateOfBirth(LocalDate.of(1990, 5, 15));
            patient.setGender(Patient.Gender.FEMALE);
            patient.setAddress("123 Main Street, City, State 12345");
            patient.setBloodGroup("A+");
            patient.setEmergencyContact("Bob Brown - 5555555556");
            patient.setMedicalHistory("No significant medical history");
            patientService.savePatient(patient);
        }

        // Create Indian patients
        createIndianPatients();

        // Create sample appointments
        createSampleAppointments();

        // Create sample bills
        createSampleBills();

        System.out.println("Initial data loaded successfully!");
    }

    private void createIndianPatients() {
        // Patient 2: Rajesh Kumar from Delhi
        if (!patientService.findByUsername("rajesh.kumar").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("rajesh.kumar");
            patient.setPassword("rajesh123");
            patient.setEmail("rajesh.kumar@gmail.com");
            patient.setFullName("Rajesh Kumar");
            patient.setPhoneNumber("+91-9876543210");
            patient.setDateOfBirth(LocalDate.of(1985, 3, 12));
            patient.setGender(Patient.Gender.MALE);
            patient.setAddress("A-45, Lajpat Nagar, New Delhi, Delhi 110024");
            patient.setBloodGroup("B+");
            patient.setEmergencyContact("Sunita Kumar - +91-9876543211");
            patient.setMedicalHistory(
                    "Diabetes Type 2, Hypertension managed with medication. Regular check-ups required.");
            patientService.savePatient(patient);
        }

        // Patient 3: Priya Sharma from Mumbai
        if (!patientService.findByUsername("priya.sharma").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("priya.sharma");
            patient.setPassword("priya123");
            patient.setEmail("priya.sharma@yahoo.in");
            patient.setFullName("Priya Sharma");
            patient.setPhoneNumber("+91-9123456789");
            patient.setDateOfBirth(LocalDate.of(1992, 7, 22));
            patient.setGender(Patient.Gender.FEMALE);
            patient.setAddress("301, Silver Heights, Andheri West, Mumbai, Maharashtra 400058");
            patient.setBloodGroup("O+");
            patient.setEmergencyContact("Vikash Sharma - +91-9123456788");
            patient.setMedicalHistory(
                    "Allergic to penicillin. Previous thyroid surgery in 2020. Currently on thyroid medication.");
            patientService.savePatient(patient);
        }

        // Patient 4: Arjun Reddy from Hyderabad
        if (!patientService.findByUsername("arjun.reddy").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("arjun.reddy");
            patient.setPassword("arjun123");
            patient.setEmail("arjun.reddy@outlook.com");
            patient.setFullName("Arjun Reddy");
            patient.setPhoneNumber("+91-9988776655");
            patient.setDateOfBirth(LocalDate.of(1988, 11, 8));
            patient.setGender(Patient.Gender.MALE);
            patient.setAddress("Plot No. 123, Jubilee Hills, Hyderabad, Telangana 500033");
            patient.setBloodGroup("AB+");
            patient.setEmergencyContact("Lakshmi Reddy - +91-9988776654");
            patient.setMedicalHistory("Asthma since childhood. Carries inhaler. No other major medical conditions.");
            patientService.savePatient(patient);
        }

        // Patient 5: Kavya Nair from Bangalore
        if (!patientService.findByUsername("kavya.nair").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("kavya.nair");
            patient.setPassword("kavya123");
            patient.setEmail("kavya.nair@gmail.com");
            patient.setFullName("Kavya Nair");
            patient.setPhoneNumber("+91-9567890123");
            patient.setDateOfBirth(LocalDate.of(1995, 1, 17));
            patient.setGender(Patient.Gender.FEMALE);
            patient.setAddress("15, Brigade Road, Bangalore, Karnataka 560001");
            patient.setBloodGroup("A-");
            patient.setEmergencyContact("Suresh Nair - +91-9567890124");
            patient.setMedicalHistory(
                    "Iron deficiency anemia. Taking iron supplements. Regular gynecological check-ups.");
            patientService.savePatient(patient);
        }

        // Patient 6: Sanjay Gupta from Pune
        if (!patientService.findByUsername("sanjay.gupta").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("sanjay.gupta");
            patient.setPassword("sanjay123");
            patient.setEmail("sanjay.gupta@rediffmail.com");
            patient.setFullName("Sanjay Gupta");
            patient.setPhoneNumber("+91-9345678901");
            patient.setDateOfBirth(LocalDate.of(1978, 9, 25));
            patient.setGender(Patient.Gender.MALE);
            patient.setAddress("Flat 402, Seasons Mall Road, Magarpatta, Pune, Maharashtra 411028");
            patient.setBloodGroup("O-");
            patient.setEmergencyContact("Neha Gupta - +91-9345678902");
            patient.setMedicalHistory(
                    "High cholesterol, on statin therapy. Mild arthritis in knees. Regular physiotherapy.");
            patientService.savePatient(patient);
        }

        // Patient 7: Meera Patel from Ahmedabad
        if (!patientService.findByUsername("meera.patel").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("meera.patel");
            patient.setPassword("meera123");
            patient.setEmail("meera.patel@gmail.com");
            patient.setFullName("Meera Patel");
            patient.setPhoneNumber("+91-9876012345");
            patient.setDateOfBirth(LocalDate.of(1991, 4, 3));
            patient.setGender(Patient.Gender.FEMALE);
            patient.setAddress("201, Shanti Niketan, Satellite, Ahmedabad, Gujarat 380015");
            patient.setBloodGroup("B-");
            patient.setEmergencyContact("Kiran Patel - +91-9876012346");
            patient.setMedicalHistory(
                    "PCOS condition managed with medication. Regular endocrinologist visits. No other complications.");
            patientService.savePatient(patient);
        }

        // Patient 8: Amit Singh from Jaipur
        if (!patientService.findByUsername("amit.singh").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("amit.singh");
            patient.setPassword("amit123");
            patient.setEmail("amit.singh@hotmail.com");
            patient.setFullName("Amit Singh");
            patient.setPhoneNumber("+91-9234567890");
            patient.setDateOfBirth(LocalDate.of(1983, 12, 14));
            patient.setGender(Patient.Gender.MALE);
            patient.setAddress("House No. 67, Civil Lines, Jaipur, Rajasthan 302006");
            patient.setBloodGroup("A+");
            patient.setEmergencyContact("Ritu Singh - +91-9234567891");
            patient.setMedicalHistory(
                    "Kidney stones removed in 2019. Advised high water intake. Regular urologist follow-ups.");
            patientService.savePatient(patient);
        }

        // Patient 9: Deepika Iyer from Chennai
        if (!patientService.findByUsername("deepika.iyer").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("deepika.iyer");
            patient.setPassword("deepika123");
            patient.setEmail("deepika.iyer@gmail.com");
            patient.setFullName("Deepika Iyer");
            patient.setPhoneNumber("+91-9789012345");
            patient.setDateOfBirth(LocalDate.of(1989, 6, 30));
            patient.setGender(Patient.Gender.FEMALE);
            patient.setAddress("12, T. Nagar, Chennai, Tamil Nadu 600017");
            patient.setBloodGroup("AB-");
            patient.setEmergencyContact("Ramesh Iyer - +91-9789012344");
            patient.setMedicalHistory(
                    "Migraine headaches, managed with preventive medication. Avoids trigger foods. No other issues.");
            patientService.savePatient(patient);
        }

        // Patient 10: Rohit Agarwal from Kolkata
        if (!patientService.findByUsername("rohit.agarwal").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("rohit.agarwal");
            patient.setPassword("rohit123");
            patient.setEmail("rohit.agarwal@gmail.com");
            patient.setFullName("Rohit Agarwal");
            patient.setPhoneNumber("+91-9456789012");
            patient.setDateOfBirth(LocalDate.of(1987, 2, 19));
            patient.setGender(Patient.Gender.MALE);
            patient.setAddress("14/2, Park Street, Kolkata, West Bengal 700016");
            patient.setBloodGroup("O+");
            patient.setEmergencyContact("Pooja Agarwal - +91-9456789013");
            patient.setMedicalHistory(
                    "Gastric ulcer treated successfully. Maintained on PPI therapy. Regular gastroenterologist visits.");
            patientService.savePatient(patient);
        }

        // Patient 11: Anita Joshi from Indore
        if (!patientService.findByUsername("anita.joshi").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("anita.joshi");
            patient.setPassword("anita123");
            patient.setEmail("anita.joshi@yahoo.co.in");
            patient.setFullName("Anita Joshi");
            patient.setPhoneNumber("+91-9678901234");
            patient.setDateOfBirth(LocalDate.of(1993, 8, 11));
            patient.setGender(Patient.Gender.FEMALE);
            patient.setAddress("56, Scheme No. 78, Vijay Nagar, Indore, Madhya Pradesh 452010");
            patient.setBloodGroup("B+");
            patient.setEmergencyContact("Mahesh Joshi - +91-9678901235");
            patient.setMedicalHistory(
                    "Fibromyalgia condition. Regular physiotherapy and pain management. Takes muscle relaxants as needed.");
            patientService.savePatient(patient);
        }

        // Patient 12: Vivek Malhotra from Chandigarh
        if (!patientService.findByUsername("vivek.malhotra").isPresent()) {
            Patient patient = new Patient();
            patient.setUsername("vivek.malhotra");
            patient.setPassword("vivek123");
            patient.setEmail("vivek.malhotra@gmail.com");
            patient.setFullName("Vivek Malhotra");
            patient.setPhoneNumber("+91-9890123456");
            patient.setDateOfBirth(LocalDate.of(1981, 10, 7));
            patient.setGender(Patient.Gender.MALE);
            patient.setAddress("SCO 45, Sector 17, Chandigarh 160017");
            patient.setBloodGroup("A-");
            patient.setEmergencyContact("Simran Malhotra - +91-9890123457");
            patient.setMedicalHistory(
                    "Sleep apnea using CPAP machine. Regular sleep clinic visits. Weight management program ongoing.");
            patientService.savePatient(patient);
        }

        System.out.println("Indian patient data loaded successfully!");
    }

    private void createSampleAppointments() {
        // Check if appointments already exist to avoid duplicates
        if (appointmentService.getAllAppointments().isEmpty()) {
            // Get doctors and patients
            Doctor drSmith = doctorService.findByUsername("dr.smith").orElse(null);
            Doctor drJohnson = doctorService.findByUsername("dr.johnson").orElse(null);
            Doctor drWilliams = doctorService.findByUsername("dr.williams").orElse(null);
            Patient patient1 = patientService.findByUsername("patient1").orElse(null);

            // Create additional patients for more appointment variety
            createAdditionalPatients();
            Patient patient2 = patientService.findByUsername("patient2").orElse(null);
            Patient patient3 = patientService.findByUsername("patient3").orElse(null);

            if (drSmith != null && drJohnson != null && drWilliams != null &&
                    patient1 != null && patient2 != null && patient3 != null) {

                // Today's appointments
                Appointment apt1 = new Appointment();
                apt1.setPatient(patient1);
                apt1.setDoctor(drSmith);
                apt1.setAppointmentDateTime(LocalDateTime.now().plusHours(2));
                apt1.setReason("Regular cardiac checkup");
                apt1.setStatus(Appointment.AppointmentStatus.CONFIRMED);
                appointmentService.saveAppointment(apt1);

                Appointment apt2 = new Appointment();
                apt2.setPatient(patient2);
                apt2.setDoctor(drJohnson);
                apt2.setAppointmentDateTime(LocalDateTime.now().plusHours(4));
                apt2.setReason("Headache and dizziness consultation");
                apt2.setStatus(Appointment.AppointmentStatus.SCHEDULED);
                appointmentService.saveAppointment(apt2);

                // Tomorrow's appointments
                Appointment apt3 = new Appointment();
                apt3.setPatient(patient3);
                apt3.setDoctor(drWilliams);
                apt3.setAppointmentDateTime(LocalDateTime.now().plusDays(1).withHour(10).withMinute(30));
                apt3.setReason("Knee pain evaluation");
                apt3.setStatus(Appointment.AppointmentStatus.SCHEDULED);
                appointmentService.saveAppointment(apt3);

                Appointment apt4 = new Appointment();
                apt4.setPatient(patient1);
                apt4.setDoctor(drJohnson);
                apt4.setAppointmentDateTime(LocalDateTime.now().plusDays(1).withHour(14).withMinute(0));
                apt4.setReason("Follow-up neurological assessment");
                apt4.setStatus(Appointment.AppointmentStatus.CONFIRMED);
                appointmentService.saveAppointment(apt4);

                // Next week appointments
                Appointment apt5 = new Appointment();
                apt5.setPatient(patient2);
                apt5.setDoctor(drSmith);
                apt5.setAppointmentDateTime(LocalDateTime.now().plusDays(7).withHour(9).withMinute(0));
                apt5.setReason("Chest pain investigation");
                apt5.setStatus(Appointment.AppointmentStatus.SCHEDULED);
                appointmentService.saveAppointment(apt5);

                Appointment apt6 = new Appointment();
                apt6.setPatient(patient3);
                apt6.setDoctor(drWilliams);
                apt6.setAppointmentDateTime(LocalDateTime.now().plusDays(5).withHour(15).withMinute(30));
                apt6.setReason("Post-surgery follow-up");
                apt6.setStatus(Appointment.AppointmentStatus.SCHEDULED);
                appointmentService.saveAppointment(apt6);

                // Past appointments (completed/cancelled)
                Appointment apt7 = new Appointment();
                apt7.setPatient(patient1);
                apt7.setDoctor(drSmith);
                apt7.setAppointmentDateTime(LocalDateTime.now().minusDays(3).withHour(11).withMinute(0));
                apt7.setReason("Annual heart checkup");
                apt7.setStatus(Appointment.AppointmentStatus.COMPLETED);
                apt7.setNotes("Patient's heart condition is stable. Prescribed medication for blood pressure.");
                apt7.setPrescription("Lisinopril 10mg once daily, Follow-up in 3 months");
                appointmentService.saveAppointment(apt7);

                Appointment apt8 = new Appointment();
                apt8.setPatient(patient2);
                apt8.setDoctor(drJohnson);
                apt8.setAppointmentDateTime(LocalDateTime.now().minusDays(1).withHour(16).withMinute(0));
                apt8.setReason("Migraine consultation");
                apt8.setStatus(Appointment.AppointmentStatus.CANCELLED);
                appointmentService.saveAppointment(apt8);

                // In-progress appointment
                Appointment apt9 = new Appointment();
                apt9.setPatient(patient3);
                apt9.setDoctor(drWilliams);
                apt9.setAppointmentDateTime(LocalDateTime.now().minusMinutes(30));
                apt9.setReason("Sports injury assessment");
                apt9.setStatus(Appointment.AppointmentStatus.IN_PROGRESS);
                appointmentService.saveAppointment(apt9);

                System.out.println("Sample appointments created successfully!");
            }
        }
    }

    private void createAdditionalPatients() {
        // Create patient2
        if (!patientService.findByUsername("patient2").isPresent()) {
            Patient patient2 = new Patient();
            patient2.setUsername("patient2");
            patient2.setPassword("patient123");
            patient2.setEmail("patient2@email.com");
            patient2.setFullName("Robert Wilson");
            patient2.setPhoneNumber("5555555557");
            patient2.setDateOfBirth(LocalDate.of(1985, 8, 22));
            patient2.setGender(Patient.Gender.MALE);
            patient2.setAddress("456 Oak Avenue, City, State 12345");
            patient2.setBloodGroup("O+");
            patient2.setEmergencyContact("Mary Wilson - 5555555558");
            patient2.setMedicalHistory("Hypertension, managed with medication");
            patientService.savePatient(patient2);
        }

        // Create patient3
        if (!patientService.findByUsername("patient3").isPresent()) {
            Patient patient3 = new Patient();
            patient3.setUsername("patient3");
            patient3.setPassword("patient123");
            patient3.setEmail("patient3@email.com");
            patient3.setFullName("Emily Davis");
            patient3.setPhoneNumber("5555555559");
            patient3.setDateOfBirth(LocalDate.of(1992, 3, 10));
            patient3.setGender(Patient.Gender.FEMALE);
            patient3.setAddress("789 Pine Street, City, State 12345");
            patient3.setBloodGroup("B+");
            patient3.setEmergencyContact("Tom Davis - 5555555560");
            patient3.setMedicalHistory("Previous knee surgery in 2020");
            patientService.savePatient(patient3);
        }
    }

    private void createSampleBills() {
        // Import necessary BigDecimal
        java.math.BigDecimal consultationFee = new java.math.BigDecimal("150.00");
        java.math.BigDecimal medicineCost = new java.math.BigDecimal("75.50");
        java.math.BigDecimal testCost = new java.math.BigDecimal("200.00");
        java.math.BigDecimal roomCharges = new java.math.BigDecimal("0.00");
        java.math.BigDecimal otherCharges = new java.math.BigDecimal("25.00");
        java.math.BigDecimal discount = new java.math.BigDecimal("0.00");

        // Get completed appointments to create bills for
        List<Appointment> completedAppointments = appointmentService.getCompletedAppointments();

        for (Appointment appointment : completedAppointments) {
            // Check if bill already exists
            if (!billService.hasAppointmentBill(appointment.getId())) {
                try {
                    // Vary the costs for different appointments
                    java.math.BigDecimal variedConsultationFee = consultationFee;
                    java.math.BigDecimal variedMedicineCost = medicineCost;
                    java.math.BigDecimal variedTestCost = testCost;
                    java.math.BigDecimal variedDiscount = discount;

                    // Add some variation based on doctor specialization
                    String specialization = appointment.getDoctor().getSpecialization();
                    if ("Cardiology".equals(specialization)) {
                        variedConsultationFee = new java.math.BigDecimal("200.00");
                        variedTestCost = new java.math.BigDecimal("350.00");
                    } else if ("Neurology".equals(specialization)) {
                        variedConsultationFee = new java.math.BigDecimal("180.00");
                        variedTestCost = new java.math.BigDecimal("300.00");
                    } else if ("Orthopedics".equals(specialization)) {
                        variedConsultationFee = new java.math.BigDecimal("170.00");
                        variedMedicineCost = new java.math.BigDecimal("100.00");
                    }

                    String notes = "Bill generated for " + appointment.getReason() + " consultation";

                    // Create the bill
                    Bill bill = billService.createBill(
                            appointment,
                            appointment.getPatient(),
                            variedConsultationFee,
                            variedMedicineCost,
                            variedTestCost,
                            roomCharges,
                            otherCharges,
                            variedDiscount,
                            notes);

                    // Mark some bills as paid (randomly)
                    if (Math.random() > 0.5) {
                        String[] paymentMethods = { "Cash", "Credit Card", "Insurance", "Bank Transfer" };
                        String paymentMethod = paymentMethods[(int) (Math.random() * paymentMethods.length)];
                        billService.markAsPaid(bill.getId(), paymentMethod);
                    }

                } catch (Exception e) {
                    System.out.println(
                            "Error creating bill for appointment " + appointment.getId() + ": " + e.getMessage());
                }
            }
        }

        System.out.println("Sample bills created successfully!");
    }
}