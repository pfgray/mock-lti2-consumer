app.service('SampleUsers', function() {

  var url = window.location.protocol + "//" + window.location.host;

  return [{
    user_id: "ccbd7af7-8777-412e-8844-7255fd3bbc7f",
    lis_person_name_full: "Harry Potter",
    roles: "Learner",
    lis_person_contact_email_primary: "hpotter@hogwarts.edu",
    lis_person_name_given: "Harry",
    lis_person_name_family: "Potter",
    user_image: url + "/assets/scripts/sampleData/images/harry.jpg"
  },
  {
    user_id: "6acba3eb-0706-4f5e-aff2-704afa3cd4ce",
    lis_person_name_full: "Hermoine Granger",
    roles: "Learner",
    lis_person_contact_email_primary: "hgranger@hogwarts.edu",
    lis_person_name_given: "Hermoine",
    lis_person_name_family: "Granger",
    user_image: url + "/assets/scripts/sampleData/images/hermoine.jpg"
  },
  {
    user_id: "acdafe48-1d86-4cd6-accf-8e0c781df79d",
    lis_person_name_full: "Ron Weasley",
    roles: "Learner",
    lis_person_contact_email_primary: "rweasley@hogwarts.edu",
    lis_person_name_given: "Ron",
    lis_person_name_family: "Weasley",
    user_image: url + "/assets/scripts/sampleData/images/ron.jpg"
  },
  {
    user_id: "620b291d-7041-4864-abbf-c6f8564f7752",
    lis_person_name_full: "Severus Snape",
    roles: "Instructor",
    lis_person_contact_email_primary: "ssnape@hogwarts.edu",
    lis_person_name_given: "Severus",
    lis_person_name_family: "Snape",
    user_image: url + "/assets/scripts/sampleData/images/severus.jpg"
  },
  {
    user_id: "87056e9a-9ba0-463e-b2d8-1576ea7cd467",
    lis_person_name_full: "Albus Dumbledore",
    roles: "urn:lti:sysrole:ims/lis/Administrator",
    lis_person_contact_email_primary: "dumbledore@hogwarts.edu",
    lis_person_name_given: "Albus",
    lis_person_name_family: "Dumbledore",
    user_image: url + "/assets/scripts/sampleData/images/albus.jpg"
  }];
});