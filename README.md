# 🏥 Hospital Management System

A comprehensive **Hospital Management System** built with **Spring Boot**, featuring role-based authentication, appointment management, billing system, and complete medical record management for Admins, Doctors, and Patients.

## 🌟 Key Features

### 🔐 **Multi-Role Authentication System**
- **Admin Portal**: Complete system administration and management
- **Doctor Portal**: Patient management, appointments, and medical consultations
- **Patient Portal**: Appointment booking, medical history, and bill management
- **Secure Role-Based Access Control**: Each role has specific permissions and dashboards

### 👥 **User Management**
- **Patient Registration**: Self-registration with comprehensive medical information
- **Doctor Management**: Admin can add/manage doctor profiles with specializations
- **Profile Management**: Users can update their personal and professional information
- **User Authentication**: Secure login system with role-based redirects

### 📅 **Advanced Appointment System** 
- **Online Appointment Booking**: Patients can book appointments with available doctors
- **Doctor Scheduling**: Doctors can manage their availability and working hours
- **Appointment Status Management**: PENDING → CONFIRMED → COMPLETED workflow
- **Real-time Appointment Tracking**: Status updates and notifications
- **Appointment History**: Complete record of past and upcoming appointments

### 💰 **Comprehensive Billing System**
- **Detailed Bill Generation**: Consultation fees, medicine costs, tests, room charges
- **Payment Status Tracking**: PENDING, PAID, CANCELLED, REFUNDED status
- **Bill Management**: Edit, view, and print bills
- **Patient Bill History**: Complete billing records for patients
- **Financial Reporting**: Revenue tracking and statistics

### 📊 **Dashboard & Analytics**
- **Admin Dashboard**: System-wide statistics, user management, appointment overview
- **Doctor Dashboard**: Personal appointments, patient statistics, availability status
- **Patient Dashboard**: Upcoming appointments, medical history, billing summary
- **Real-time Statistics**: Live counts and status updates

### 🏥 **Medical Records Management**
- **Patient Medical History**: Comprehensive health records and conditions
- **Doctor Prescriptions**: Post-consultation notes and prescriptions
- **Appointment Notes**: Detailed consultation records
- **Medical Information Tracking**: Blood group, allergies, emergency contacts

## 🛠️ Technology Stack

### Backend
- **Spring Boot 2.7.18**: Main application framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations and ORM
- **Spring Web MVC**: Web layer and REST APIs
- **Validation**: Bean validation with custom validators

### Frontend
- **Thymeleaf**: Server-side template engine
- **Bootstrap 5.3.0**: Responsive UI framework
- **Font Awesome**: Icon library
- **JavaScript/jQuery**: Interactive features
- **HTML5 & CSS3**: Modern web standards

### Database
- **H2 In-Memory Database**: Development and testing
- **Hibernate**: JPA implementation
- **Automatic Schema Generation**: DDL auto-creation

### Build & Development
- **Maven**: Dependency management and build tool
- **Spring Boot DevTools**: Hot reloading during development
- **Java 8+**: Programming language

## 🚀 Getting Started

### Prerequisites

- **Java 8 or higher** installed
- **Maven 3.6+** for build management
- **Modern web browser** (Chrome, Firefox, Safari, Edge)
- **Internet connection** for Bootstrap CDN (optional)

### Installation & Setup

1. **Clone or download the project**
   ```bash
   # If using git
   git clone <repository-url>
   cd HMS
   
   # Or download and extract the ZIP file
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Open your browser and navigate to: `http://localhost:8080`
   - The application will automatically create sample data on first startup
   - The application will be running on port 8080

### 💾 Database Access

- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:hmsdb`
- **Username**: `sa`
- **Password**: *(leave empty)*

## 🏗️ Application Structure

### User Roles & Access

1. **👑 Admin Portal** (`/admin/**`)
   - System administration
   - User management
   - Hospital operations oversight

2. **👨‍⚕️ Doctor Portal** (`/doctor/**`)
   - Patient management
   - Appointment scheduling
   - Medical records

3. **🧑‍🤝‍🧑 Patient Portal** (`/patient/**`)
   - Appointment booking
   - Medical history
   - Profile management

## 🔑 Default Login Credentials

*Note: Sample users are created automatically when the application starts*

### 👑 Admin Login
- **Username**: `admin`
- **Password**: `admin123`
- **Email**: `admin@hospital.com`
- **Access**: Full system administration
- **Dashboard**: `http://localhost:8080/admin/dashboard`

### 👨‍⚕️ Doctor Login Options
*Multiple doctors are available with different specializations*

#### Primary Doctor Account
- **Username**: `dr.smith`
- **Password**: `doctor123`
- **Email**: `dr.smith@hospital.com`
- **Full Name**: Dr. John Smith
- **Specialization**: Cardiology
- **Experience**: 10 years
- **Consultation Fee**: ₹800
- **Working Hours**: 9:00 AM - 5:00 PM

#### Additional Doctor Accounts
| Username | Password | Name | Specialization | Experience | Fee |
|----------|----------|------|----------------|------------|-----|
| `dr.johnson` | `doctor123` | Dr. Sarah Johnson | Neurology | 8 years | ₹1000 |
| `dr.williams` | `doctor123` | Dr. Michael Williams | Orthopedics | 12 years | ₹750 |
| `dr.sharma` | `doctor123` | Dr. Rajesh Sharma | General Medicine | 15 years | ₹600 |
| `dr.patel` | `doctor123` | Dr. Priya Patel | Gynecology | 10 years | ₹900 |
| `dr.singh` | `doctor123` | Dr. Amanjeet Singh | Pediatrics | 8 years | ₹700 |
| `dr.gupta` | `doctor123` | Dr. Anita Gupta | Dermatology | 12 years | ₹800 |
| `dr.kumar` | `doctor123` | Dr. Vikash Kumar | ENT | 9 years | ₹650 |
| `dr.reddy` | `doctor123` | Dr. Lakshmi Reddy | Ophthalmology | 14 years | ₹950 |
| `dr.agarwal` | `doctor123` | Dr. Sanjay Agarwal | Psychiatry | 11 years | ₹1200 |

### 🧑‍🤝‍🧑 Patient Login Options
*Multiple patient accounts with different medical histories*

#### Primary Patient Account
- **Username**: `patient1`
- **Password**: `patient123`
- **Email**: `patient1@email.com`
- **Full Name**: Alice Brown
- **Blood Group**: A+
- **Emergency Contact**: Bob Brown - 5555555556

#### Additional Patient Accounts
| Username | Password | Name | Location | Blood Group | Medical History |
|----------|----------|------|----------|-------------|-----------------|
| `rajesh.kumar` | `rajesh123` | Rajesh Kumar | New Delhi | B+ | Diabetes Type 2, Hypertension |
| `priya.sharma` | `priya123` | Priya Sharma | Mumbai | O+ | Allergic to penicillin, Thyroid |
| `arjun.menon` | `arjun123` | Arjun Menon | Kerala | A+ | Asthma since childhood |
| `kavya.nair` | `kavya123` | Kavya Nair | Bangalore | A- | Generally healthy |
| `sanjay.gupta` | `sanjay123` | Sanjay Gupta | Pune | O- | High cholesterol, Arthritis |
| `meera.patel` | `meera123` | Meera Patel | Ahmedabad | B- | PCOS condition |
| `amit.singh` | `amit123` | Amit Singh | Jaipur | A+ | Kidney stones (treated) |
| `deepika.iyer` | `deepika123` | Deepika Iyer | Chennai | AB- | Migraine headaches |
| `rohit.agarwal` | `rohit123` | Rohit Agarwal | Kolkata | O+ | Gastric ulcer (treated) |
| `anita.joshi` | `anita123` | Anita Joshi | Indore | B+ | Fibromyalgia condition |
| `vivek.malhotra` | `vivek123` | Vivek Malhotra | Chandigarh | A- | Sleep apnea with CPAP |
| `patient2` | `patient123` | Robert Wilson | City, State | O+ | Hypertension |
| `patient3` | `patient123` | Emily Davis | City, State | A- | No major conditions |

**Dashboard Access**: `http://localhost:8080/patient/dashboard`

## 🌐 Application URLs

### Public Pages
- **Home Page**: `http://localhost:8080/`
- **Login**: `http://localhost:8080/login`
- **Registration**: `http://localhost:8080/register`

### Role-Specific Dashboards
- **Admin Dashboard**: `http://localhost:8080/admin/dashboard`
- **Doctor Dashboard**: `http://localhost:8080/doctor/dashboard` 
- **Patient Dashboard**: `http://localhost:8080/patient/dashboard`

### Development Tools
- **H2 Database Console**: `http://localhost:8080/h2-console`

## 📁 Project Architecture

### Package Structure
```
com.hms/
├── 🏠 HospitalManagementSystemApplication.java # Main application class
├── ⚙️ config/
│   ├── SecurityConfig.java              # Security configuration
│   ├── CustomAuthenticationSuccessHandler.java # Login redirect handler
│   └── DataLoader.java                  # Sample data initialization
├── 📊 entity/
│   ├── User.java                        # Base user entity
│   ├── Admin.java                       # Admin entity
│   ├── Doctor.java                      # Doctor entity
│   ├── Patient.java                     # Patient entity
│   └── Appointment.java                 # Appointment entity
├── 🗄️ repository/
│   ├── UserRepository.java
│   ├── AdminRepository.java
│   ├── DoctorRepository.java
│   ├── PatientRepository.java
│   └── AppointmentRepository.java
├── 🔧 service/
│   ├── UserService.java
│   ├── CustomUserDetailsService.java
│   ├── PatientService.java
│   ├── DoctorService.java
│   └── AppointmentService.java
└── 🌐 controller/
    ├── HomeController.java
    ├── AdminController.java
    ├── DoctorController.java
    └── PatientController.java
```

### 🗄️ Database Schema

#### Users Table (Base table for all users)
| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT | Primary Key |
| `username` | VARCHAR(20) | Unique username |
| `password` | VARCHAR(255) | BCrypt encrypted |
| `email` | VARCHAR(255) | Unique email |
| `full_name` | VARCHAR(255) | Display name |
| `phone_number` | VARCHAR(255) | Contact number |
| `role` | VARCHAR(255) | ADMIN/DOCTOR/PATIENT |
| `enabled` | BOOLEAN | Account status |
| `created_at` | TIMESTAMP | Creation time |
| `updated_at` | TIMESTAMP | Last update |

#### Admins Table (Extends Users)
| Column | Type | Description |
|--------|------|-------------|
| `user_id` | BIGINT | Foreign Key to Users |
| `employee_id` | VARCHAR(50) | Employee identifier |
| `department` | VARCHAR(100) | Admin department |
| `admin_level` | VARCHAR(255) | Admin privilege level |

#### Doctors Table (Extends Users)
| Column | Type | Description |
|--------|------|-------------|
| `user_id` | BIGINT | Foreign Key to Users |
| `license_number` | VARCHAR(255) | Unique medical license |
| `specialization` | VARCHAR(255) | Medical specialty |
| `experience_years` | INTEGER | Years of experience |
| `qualifications` | VARCHAR(1000) | Educational background |
| `consultation_fee` | VARCHAR(500) | Fee information |
| `working_hours` | VARCHAR(200) | Available hours |
| `available_for_consultation` | BOOLEAN | Availability status |

#### Patients Table (Extends Users)
| Column | Type | Description |
|--------|------|-------------|
| `user_id` | BIGINT | Foreign Key to Users |
| `date_of_birth` | DATE | Patient's DOB |
| `gender` | VARCHAR(255) | Gender |
| `address` | VARCHAR(500) | Home address |
| `blood_group` | VARCHAR(20) | Blood type |
| `emergency_contact` | VARCHAR(50) | Emergency number |
| `medical_history` | VARCHAR(1000) | Medical background |

#### Appointments Table
| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT | Primary Key |
| `patient_id` | BIGINT | Foreign Key to Patients |
| `doctor_id` | BIGINT | Foreign Key to Doctors |
| `appointment_date_time` | TIMESTAMP | Scheduled time |
| `reason` | VARCHAR(500) | Visit reason |
| `status` | VARCHAR(255) | SCHEDULED/CONFIRMED/COMPLETED/CANCELLED |
| `notes` | VARCHAR(1000) | Doctor's notes |
| `prescription` | VARCHAR(1000) | Prescribed treatment |
| `created_at` | TIMESTAMP | Creation time |
| `updated_at` | TIMESTAMP | Last update |

## ✅ Features Implementation Status

### ✅ Completed Features
- ✅ Multi-role authentication system
- ✅ User registration and login
- ✅ Role-based access control
- ✅ Responsive web interface
- ✅ Database schema with relationships
- ✅ Sample data loading
- ✅ Security configuration
- ✅ Basic dashboards for each role
- ✅ Form validation
- ✅ Password encryption
- ✅ Session management

### 🚧 Ready for Enhancement
- 🔄 Advanced appointment booking
- 📅 Doctor availability calendar
- 📋 Patient medical records management
- 👥 Admin user management interface
- 📧 Email notifications
- 📊 Report generation
- 🔍 Advanced search and filtering
- 📱 Mobile responsive improvements

## 🧪 Testing the Application

### 1. Start the Application
```bash
mvn spring-boot:run
```

### 2. Test Login Flow
1. **Visit**: `http://localhost:8080`
2. **Click**: "Login" button
3. **Try each role**:
   - Admin: `admin` / `admin123`
   - Doctor: `dr.smith` / `doctor123`
   - Patient: `patient1` / `patient123`

### 3. Test Registration
1. **Visit**: `http://localhost:8080/register`
2. **Fill form** with valid data
3. **Select role** (Patient/Doctor)
4. **Submit** and verify redirect

### 4. Test Database
1. **Visit**: `http://localhost:8080/h2-console`
2. **Connect** with JDBC URL: `jdbc:h2:mem:hmsdb`
3. **Browse tables**: USERS, ADMINS, DOCTORS, PATIENTS, APPOINTMENTS

## 🔧 Development Guidelines

### Adding New Features

1. **📊 Create Entity**: Define JPA entity in `entity/` package
2. **🗄️ Create Repository**: Extend JpaRepository in `repository/` package
3. **🔧 Create Service**: Implement business logic in `service/` package
4. **🌐 Create Controller**: Handle web requests in `controller/` package
5. **📝 Create Templates**: Add Thymeleaf templates in `templates/` folder
6. **🔒 Update Security**: Modify SecurityConfig if needed

### Code Style
- Follow Spring Boot conventions
- Use meaningful variable names
- Add proper validation annotations
- Include error handling
- Write service-level business logic

## 🛠️ Troubleshooting

### Common Issues

#### 🚫 Port 8080 already in use
**Solution**: Change port in `application.properties`
```properties
server.port=8081
```

#### 🔌 Database connection issues
**Solution**: 
- Check H2 console with JDBC URL: `jdbc:h2:mem:hmsdb`
- Verify application is running
- Check console logs for errors

#### 🔑 Login failures
**Solutions**:
- Verify sample data is loaded (check console logs)
- Use exact credentials listed above
- Clear browser cache
- Check for typos in username/password

#### 🚷 Access denied errors
**Solutions**:
- Ensure user has correct role permissions
- Check SecurityConfig for URL patterns
- Verify user is properly authenticated
- Check session timeout

#### 🏗️ Build failures
**Solutions**:
```bash
# Clean and rebuild
mvn clean compile

# Skip tests if needed
mvn clean compile -DskipTests

# Check Java version
java -version
```

### 📋 Application Logs to Monitor

- ✅ Database table creation
- ✅ Sample data loading (Admin, Doctor, Patient creation)
- ✅ Security configuration loading
- ✅ HTTP request handling
- ❌ Authentication failures
- 🚨 Exception traces

## 🔒 Security Features

- **🔐 Password Encryption**: BCrypt hashing with salt
- **🛡️ CSRF Protection**: Enabled by default
- **🔑 Session Management**: Secure session handling
- **👮 Role-Based Authorization**: Method and URL-level security
- **✅ Input Validation**: Bean validation on forms
- **🔄 Authentication Success Handler**: Custom redirect logic
- **🚪 Logout Protection**: Secure logout with session invalidation

## 📈 Performance Considerations

- **Database**: H2 in-memory database for development (fast startup)
- **Connection Pooling**: HikariCP for efficient connections
- **Static Resources**: Cached CSS/JS files
- **Lazy Loading**: JPA lazy fetch strategies
- **Session Timeout**: Configurable session management

## 🚀 Complete Feature Documentation

### 👑 Admin Portal Features (`/admin/**`)

#### Dashboard (`/admin/dashboard`)
- **System Statistics**: Total patients, doctors, appointments count
- **Today's Appointments**: Current day appointment overview
- **Recent Activity**: Latest system activities
- **Quick Actions**: Direct access to management functions

#### Patient Management (`/admin/patients`)
- **View All Patients**: Complete patient list with search/filter
- **Patient Details**: Medical history, contact information, appointments
- **Edit Patient Information**: Update patient records
- **Patient Statistics**: Active patients, new registrations
- **Medical Records Access**: View complete patient history

#### Doctor Management (`/admin/doctors`)
- **View All Doctors**: List of all doctors with specializations
- **Add New Doctor**: Register new doctors in the system
- **Doctor Profile Management**: Edit doctor information and credentials
- **Availability Control**: Toggle doctor availability status
- **Doctor Statistics**: Available doctors, busy doctors, new additions
- **Schedule Overview**: View doctor schedules and appointments

#### Appointment Management (`/admin/appointments`)
- **View All Appointments**: System-wide appointment overview
- **Create Appointments**: Book appointments for patients
- **Appointment Status Control**: Confirm, cancel, or reschedule
- **Appointment Analytics**: Today's count, pending confirmations
- **Appointment History**: Complete appointment records
- **Doctor Schedule Integration**: Real-time availability checking

#### Billing System (`/bills/admin`)
- **Bills Overview**: All bills with payment status
- **Create New Bills**: Generate bills for completed appointments
- **Edit Bills**: Modify bill details and charges
- **Payment Management**: Mark bills as paid, track payment methods
- **Financial Reports**: Revenue tracking and statistics
- **Bill Categories**: Consultation, medicine, tests, room charges
- **Print Bills**: Generate printable bill formats

### 👨‍⚕️ Doctor Portal Features (`/doctor/**`)

#### Doctor Dashboard (`/doctor/dashboard`)
- **Personal Statistics**: Upcoming appointments, today's schedule
- **Patient Count**: Total patients treated
- **Quick Appointment Actions**: Accept, reject, complete appointments
- **Availability Status**: Toggle consultation availability
- **Schedule Overview**: Personal daily/weekly schedule

#### Appointment Management (`/doctor/appointments`)
- **View Appointments**: Personal appointment list with patient details
- **Appointment Actions**:
  - **Accept**: Confirm scheduled appointments
  - **Reject**: Decline appointments with automatic notification
  - **Complete**: Mark appointments as completed with notes
- **Appointment Details**: Patient information, reason for visit
- **Prescription Management**: Add post-consultation prescriptions
- **Medical Notes**: Document consultation findings

#### Profile Management (`/doctor/profile`)
- **Personal Information**: Update contact details, qualifications
- **Professional Details**: Specialization, license number, experience
- **Consultation Fees**: Set and update consultation charges
- **Working Hours**: Define availability schedule
- **Availability Toggle**: Enable/disable consultation availability

### 🧑‍🤝‍🧑 Patient Portal Features (`/patient/**`)

#### Patient Dashboard (`/patient/dashboard`)
- **Upcoming Appointments**: Next scheduled visits
- **Medical Summary**: Recent consultations and prescriptions
- **Bill Status**: Outstanding payments and recent bills
- **Quick Actions**: Book appointment, view doctors

#### Appointment Booking (`/patient/book-appointment`)
- **Doctor Search**: Find doctors by specialization
- **Doctor Profiles**: View doctor details, fees, availability
- **Schedule Selection**: Choose preferred appointment time
- **Reason for Visit**: Specify consultation purpose
- **Confirmation**: Appointment booking confirmation

#### Doctor Directory (`/patient/doctors`)
- **Complete Doctor List**: All available doctors
- **Specialization Filter**: Filter by medical specialty
- **Doctor Details**: Qualifications, experience, consultation fees
- **Working Hours**: Doctor availability times
- **Contact Information**: Doctor contact details

#### Appointment History (`/patient/appointments`)
- **All Appointments**: Past and upcoming appointments
- **Appointment Status**: Track appointment progress
- **Cancel Appointments**: Cancel upcoming appointments
- **Medical Records**: View consultation notes and prescriptions
- **Appointment Details**: Complete appointment information

#### Bill Management (`/bills/patient`)
- **Bill History**: All patient bills with payment status
- **Bill Details**: Itemized billing with all charges
- **Print Bills**: Generate printable bill copies
- **Payment Status**: Track paid and pending bills
- **Total Outstanding**: Summary of unpaid amounts

#### Profile Management (`/patient/profile`)
- **Personal Information**: Update contact and demographic details
- **Medical Information**: Blood group, allergies, medical history
- **Emergency Contacts**: Update emergency contact information
- **Address**: Update residential address

### 💰 Comprehensive Billing System Features

#### Bill Components
- **Consultation Fee**: Doctor's consultation charges
- **Medicine Cost**: Prescribed medication costs
- **Test/Lab Charges**: Diagnostic test fees
- **Room Charges**: Hospital room/facility charges
- **Other Charges**: Additional services
- **Discount**: Applied discounts and concessions
- **Total Amount**: Calculated final amount

#### Payment Management
- **Payment Status**: PENDING, PAID, CANCELLED, REFUNDED
- **Payment Methods**: Cash, Card, Online, Insurance
- **Payment Date**: Track payment completion date
- **Receipt Generation**: Printable payment receipts

#### Financial Reporting
- **Revenue Statistics**: Total revenue, monthly collection
- **Outstanding Amount**: Pending payments tracking
- **Payment Analytics**: Payment method distribution
- **Monthly Reports**: Revenue trends and patterns

## 🔄 Application Workflow

### Patient Registration → Appointment → Consultation → Billing

1. **Patient Registration**: New patient creates account with medical details
2. **Doctor Selection**: Patient browses doctors by specialization
3. **Appointment Booking**: Patient books appointment with preferred doctor
4. **Admin Confirmation**: Admin can confirm appointment if needed
5. **Doctor Consultation**: Doctor accepts and completes appointment
6. **Bill Generation**: System generates bill after consultation
7. **Payment Processing**: Patient views and pays bill
8. **Medical Records**: Consultation notes added to patient history

### Role-Based Access Control

#### Admin Access
- Full system control and oversight
- User management (patients and doctors)
- Appointment coordination and management
- Financial oversight and billing management
- System statistics and reporting

#### Doctor Access
- Personal appointment management
- Patient consultation and medical records
- Prescription and medical notes
- Personal profile and availability management

#### Patient Access
- Personal appointment booking and history
- Medical records and prescription viewing
- Bill viewing and payment status
- Personal profile management
- Doctor directory access

## 🔒 Security Implementation Details

### Authentication
- **Spring Security**: Comprehensive security framework
- **BCrypt Password Encoding**: Secure password hashing
- **Role-Based Authentication**: ADMIN, DOCTOR, PATIENT roles
- **Custom Login Success Handler**: Role-based redirect after login

### Authorization 
- **Method-Level Security**: `@PreAuthorize` annotations
- **URL-Based Security**: Route protection by role
- **CSRF Protection**: Cross-site request forgery prevention
- **Session Management**: Secure session handling

### Data Protection
- **Input Validation**: Bean validation on all forms
- **SQL Injection Prevention**: JPA parameter binding
- **XSS Protection**: Thymeleaf template escaping
- **Sensitive Data**: Encrypted password storage

## 📱 User Interface Features

### Responsive Design
- **Bootstrap 5.3.0**: Mobile-first responsive framework
- **Font Awesome Icons**: Professional iconography
- **Custom CSS**: Hospital-themed styling
- **Interactive Elements**: JavaScript-enhanced forms

### User Experience
- **Intuitive Navigation**: Role-based menu systems
- **Form Validation**: Real-time input validation
- **Success/Error Messages**: User feedback for all actions
- **Loading States**: Progress indicators for operations
- **Print-Friendly**: Optimized printing for bills and reports

### Accessibility
- **Semantic HTML**: Proper HTML structure
- **ARIA Labels**: Screen reader support
- **Keyboard Navigation**: Full keyboard accessibility
- **Color Contrast**: Accessible color schemes

## 🗄️ Database Design & Relationships

### Entity Relationships
- **User → Admin/Doctor/Patient**: Inheritance hierarchy
- **Doctor ↔ Appointment ↔ Patient**: Many-to-many through appointments
- **Appointment → Bill**: One-to-one relationship
- **Patient → Bill**: One-to-many relationship

### Data Integrity
- **Foreign Key Constraints**: Referential integrity
- **Unique Constraints**: Username and email uniqueness
- **Not Null Constraints**: Required field validation
- **Check Constraints**: Data validation at database level

## 🧪 Sample Data Included

The application automatically creates comprehensive sample data including:
- **1 Admin**: System administrator account
- **10 Doctors**: Various specializations and experience levels
- **15+ Patients**: Diverse medical histories and demographics
- **Sample Appointments**: Past and upcoming appointments
- **Sample Bills**: Various billing scenarios and payment status

This sample data allows immediate testing of all system features without manual data entry.

## 📈 Performance Considerations

- **Database**: H2 in-memory database for development (fast startup)
- **Connection Pooling**: HikariCP for efficient connections
- **Static Resources**: Cached CSS/JS files
- **Lazy Loading**: JPA lazy fetch strategies
- **Session Timeout**: Configurable session management
- **Optimized Queries**: Efficient database queries
- **Caching**: Application-level caching where appropriate

## 🎯 Future Enhancement Opportunities

### Immediate Enhancements
- **Email Notifications**: Appointment confirmations and reminders
- **SMS Integration**: Text message notifications
- **Advanced Reporting**: Detailed analytics and reports
- **Calendar Integration**: Google Calendar sync
- **File Uploads**: Medical document attachments

### Advanced Features
- **Multi-Hospital Support**: Support for multiple hospital branches
- **Inventory Management**: Medical supplies and equipment tracking
- **Staff Management**: Nurse and other staff role management
- **Insurance Integration**: Insurance claim processing
- **Telemedicine**: Video consultation capabilities
- **Mobile App**: Dedicated mobile applications
- **API Development**: RESTful APIs for third-party integration

This Hospital Management System provides a solid foundation for healthcare facility management with room for extensive customization and enhancement based on specific institutional needs.

## 🔄 Deployment Options

### Development
```bash
mvn spring-boot:run
```

### Production JAR
```bash
mvn clean package
java -jar target/hospital-management-system-1.0.0.jar
```

### Docker (Optional)
```dockerfile
FROM openjdk:8-jre-slim
COPY target/hospital-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is created for educational and demonstration purposes.

---

## 📞 Quick Reference

| Component | URL/Command |
|-----------|-------------|
| 🏠 **Home** | `http://localhost:8080/` |
| 🔑 **Login** | `http://localhost:8080/login` |
| 📝 **Register** | `http://localhost:8080/register` |
| 👑 **Admin** | `admin` / `admin123` |
| 👨‍⚕️ **Doctor** | `dr.smith` / `doctor123` |
| 🧑‍🤝‍🧑 **Patient** | `patient1` / `patient123` |
| 🗄️ **Database** | `http://localhost:8080/h2-console` |
| ▶️ **Run** | `mvn spring-boot:run` |
| 🏗️ **Build** | `mvn clean compile` |

**🚀 Quick Start**: Run `mvn spring-boot:run` and visit `http://localhost:8080`

**📧 Contact**: Hospital Management System - Spring Boot Demo Project