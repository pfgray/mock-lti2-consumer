package net.paulgray.mocklti2.tools

import javax.servlet.http.HttpServletRequest

import net.paulgray.mocklti2.web.HttpUtils
import net.paulgray.mocklti2.web.entities._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, RequestParam}

import scala.collection.JavaConverters._

@Controller
class MembershipsController {

  @Autowired
  var gradebooksService: GradebooksService = null

  @RequestMapping(value = Array("/contexts/{contextId}/memberships"), method = Array(RequestMethod.GET))
  def getMemberships(
    @PathVariable(value = "contextId") contextId: String,
    @RequestParam(value = "role", required = false) role: String,
    @RequestParam(value = "rlid", required = false) rlid: String,
    req: HttpServletRequest
  ): ResponseEntity[Page[MembershipContainer]] = {
    val origin = HttpUtils.getOrigin(req).orElse("")

    val members = List(
      new Membership(
        "Active",
        "Learner",
        new Person(
          "ccbd7af7-8777-412e-8844-7255fd3bbc7f",
          "ccbd7af7-8777-412e-8844-7255fd3bbc7f",
          "hpotter@hogwarts.edu",
          "Potter",
          "Harry Potter",
          "Harry",
          s"$origin/assets/scripts/sampleData/images/harry.jpg"
        )
      ),
      new Membership(
        "Active",
        "Learner",
        new Person(
          "6acba3eb-0706-4f5e-aff2-704afa3cd4ce",
          "6acba3eb-0706-4f5e-aff2-704afa3cd4ce",
          "hgranger@hogwarts.edu",
          "Granger",
          "Hermoine Granger",
          "Hermoine",
          s"$origin/assets/scripts/sampleData/images/hermoine.jpg"
        )
      ),
      new Membership(
        "Active",
        "Learner",
        new Person(
          "acdafe48-1d86-4cd6-accf-8e0c781df79d",
          "acdafe48-1d86-4cd6-accf-8e0c781df79d",
          "rweasley@hogwarts.edu",
          "Weasley",
          "Ron Weasley",
          "Ron",
          s"$origin/assets/scripts/sampleData/images/ron.jpg"
        )
      ),
      new Membership(
        "Active",
        "Instructor",
        new Person(
          "620b291d-7041-4864-abbf-c6f8564f7752",
          "620b291d-7041-4864-abbf-c6f8564f7752",
          "ssnape@hogwarts.edu",
          "Snape",
          "Severus Snape",
          "Severus",
          s"$origin/assets/scripts/sampleData/images/severus.jpg"
        )
      ),
      new Membership(
        "Active",
        "urn:lti:sysrole:ims/lis/Administrator",
        new Person(
          "87056e9a-9ba0-463e-b2d8-1576ea7cd467",
          "87056e9a-9ba0-463e-b2d8-1576ea7cd467",
          "dumbledore@hogwarts.edu",
          "Dumbledore",
          "Albus Dumbledore",
          "Albus",
          s"$origin/assets/scripts/sampleData/images/albus.jpg"
        )
      )
    )

    val container = new MembershipContainer(new MembershipSubject(contextId.toString, members.asJava))

    val pg = new Page(s"$origin/contexts/$contextId/memberships", null, null, container)

    new ResponseEntity(pg, HttpStatus.OK)
  }

}
