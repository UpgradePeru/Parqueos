using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Model
{
    public class Alquiler
    {
        public int Id { get; set; }

        public int ParqueoId { get; set; }
        public Parqueo Parqueo { get; set; }

        [Column(TypeName = "date")]
        public DateTime Fecha { get; set; }

        public int HoraId { get; set; }
        public Hora Hora { get; set; }

        public string UserId { get; set; }

        [Column(TypeName = "decimal(9, 2)")]
        public decimal Price { get; set; }

        public DateTime FechaCreado { get; set; }

        public DateTime FechaPagado { get; set; }

        public DateTime FechaConfirmado { get; set; }

        public int Estado { get; set; }  //-1 cancelado, 0 reservado, 1 pagado, 2 confirmado



    }
}
