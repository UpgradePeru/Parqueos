using System.ComponentModel.DataAnnotations.Schema;

namespace Model
{
    public class Parqueo
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }

        [Column(TypeName = "decimal(9, 2)")]
        public decimal Price { get; set; }

        public string Contact { get; set; }
        public string Address { get; set; }

        [Column(TypeName = "decimal(18, 10)")]
        public decimal Latitude { get; set; }
        [Column(TypeName = "decimal(18, 10)")]
        public decimal Longitude { get; set; }

        public string UrlImg { get; set; }

        public int DistritoId { get; set; }
        public Distrito Distrito { get; set; }

        public bool Enable { get; set; }

    }
}
