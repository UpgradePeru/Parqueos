using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;

namespace Model.Identity
{
    public class ApplicationUser : IdentityUser
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }

        public DateTime? Birthday { get; set; }

        public string NroDoc { get; set; }
        public string Celular { get; set; }
        public string Licencia { get; set; }
        public string Placa { get; set; }

        public List<ApplicationUserRole> UserRoles { get; set; }
    }
}