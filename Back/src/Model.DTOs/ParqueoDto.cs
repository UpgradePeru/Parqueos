using System.ComponentModel.DataAnnotations;

namespace Model.DTOs
{
    public class ParqueoCreateDto
    {
        [Required]
        public string Name { get; set; }

        public string Description { get; set; }
        public decimal Price { get; set; }

        public string Contact { get; set; }
        public string Address { get; set; }

        public decimal Latitude { get; set; }
        public decimal Longitude { get; set; }

        //public string UrlImg { get; set; }

        public int DistritoId { get; set; }
        public bool Enable { get; set; }
    }

    public class ParqueoUpdateDto
    {
        [Required]
        public string Name { get; set; }
        
        public string Description { get; set; }
        public decimal Price { get; set; }

        public string Contact { get; set; }
        public string Address { get; set; }

        public decimal Latitude { get; set; }
        public decimal Longitude { get; set; }

        //public string UrlImg { get; set; }

        public int DistritoId { get; set; }
        public bool Enable { get; set; }
    }

    public class ParqueoDto
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public string Description { get; set; }
        public decimal Price { get; set; }

        public string Contact { get; set; }
        public string Address { get; set; }

        public decimal Latitude { get; set; }
        public decimal Longitude { get; set; }

        public string UrlImg { get; set; }

        public int DistritoId { get; set; }
        public bool Enable { get; set; }
    }
}
