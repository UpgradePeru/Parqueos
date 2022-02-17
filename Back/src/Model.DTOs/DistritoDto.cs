using System.ComponentModel.DataAnnotations;

namespace Model.DTOs
{
    public class DistritoCreateDto
    {
        [Required]
        public string Name { get; set; }
        public string Description { get; set; }
    }

    public class DistritoUpdateDto
    {
        [Required]
        public string Name { get; set; }
        public string Description { get; set; }
    }

    public class DistritoDto
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
    }
}
