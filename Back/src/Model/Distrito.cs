using System.Collections.Generic;

namespace Model
{
    public class Distrito
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }

        public List<Parqueo> Parqueos { get; set; }

    }
}
