using System;
using System.ComponentModel.DataAnnotations;

namespace Model.DTOs
{
    public class AlquilerCreateDto
    {
        [Required]
        public int ParqueoId { get; set; }
        [Required]
        public DateTime Fecha { get; set; }
        [Required]
        public int HoraId { get; set; }

        public string UserId { get; set; }

        public decimal Price { get; set; }
    }

    public class AlquilerUpdateDto
    {
        [Required]
        public int Estado { get; set; }
    }

    public class AlquilerDto
    {
        public int Id { get; set; }
        public int ParqueoId { get; set; }
        public ParqueoDto Parqueo { get; set; }

        public DateTime Fecha { get; set; }

        public int HoraId { get; set; }
        public HoraDto Hora { get; set; }


        public string UserId { get; set; }

        public decimal Price { get; set; }

        public DateTime FechaCreado { get; set; }

        public DateTime FechaPagado { get; set; }

        public DateTime FechaConfirmado { get; set; }

        public int Estado { get; set; }
    }
}
