using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace GLIFRP.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Title = "GLIFRP";

            return View();
        }

        public JsonResult GetInfo()
        {
            Person[] people = new Person[2];
            people[0] = new Person("John", "Doe");
            people[1] = new Person("Anna", "Smith");
            //var result = Newtonsoft.Json.JsonConvert.SerializeObject(people[0]);
            string result = "{\"firstName\" : \"John\"}";
            return Json(result, JsonRequestBehavior.AllowGet);
        }
    }

    class Person
    {
        string firstName
        {
            get;
            set;
        }
        string lastName
        {
            get;
            set;
        }

        public Person(string first, string last)
        {
            firstName = first;
            lastName = last;
        }
    }
}
