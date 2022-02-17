using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Model;

namespace Persistence.Database.Config
{
    public class DistritoConfig
    {
        public DistritoConfig(EntityTypeBuilder<Distrito> entityBuilder)
        {
            entityBuilder.Property(x => x.Name).IsRequired().HasMaxLength(100);
        }
    }
}
