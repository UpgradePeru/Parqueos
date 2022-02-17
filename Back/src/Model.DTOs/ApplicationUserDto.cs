using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace Model.DTOs
{
    public class ApplicationUserDto 
    {
        public string Id { get; set; }
        public string FullName { get; set; }
        public string Email { get; set; }
        public List<string> Roles { get; set; }
    }

    public class ApplicationUserRegisterDto
    {
        [Required]
        public string FirstName { get; set; }
        //[Required]
        public string LastName { get; set; }
        [Required]
        public string Email { get; set; }
        [Required]
        public string Password { get; set; }

        public string NroDoc { get; set; }
        public string Celular { get; set; }
        public string Licencia { get; set; }
        public string Placa { get; set; }

    }

    public class ApplicationUserLoginDto
    {
        [Required]
        public string Email { get; set; }
        [Required]
        public string Password { get; set; }
    }
}
